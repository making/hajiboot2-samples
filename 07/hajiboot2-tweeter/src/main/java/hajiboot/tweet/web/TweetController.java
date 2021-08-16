package hajiboot.tweet.web;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import hajiboot.PaginatedResult;
import hajiboot.tweet.Tweet;
import hajiboot.tweet.TweetMapper;
import hajiboot.tweeter.Tweeter;
import hajiboot.tweeter.TweeterMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.IdGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class TweetController {
	private final TweetMapper tweetMapper;

	private final TweeterMapper tweeterMapper;

	private final IdGenerator idGenerator;

	private final Clock clock;

	public TweetController(TweetMapper tweetMapper, TweeterMapper tweeterMapper, IdGenerator idGenerator, Clock clock) {
		this.tweetMapper = tweetMapper;
		this.tweeterMapper = tweeterMapper;
		this.idGenerator = idGenerator;
		this.clock = clock;
	}

	@GetMapping(path = "tweets/{uuid}")
	public ResponseEntity<?> getTweet(@PathVariable UUID uuid) {
		try {
			final Tweet tweet = this.tweetMapper.findByUuid(uuid);
			final TweetOutput output = TweetOutput.fromTweet(tweet);
			return ResponseEntity.ok(output);
		}
		catch (EmptyResultDataAccessException ignored) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("error", "Not Found",
							"status", 404,
							"message", String.format("The given uuid is not found (uuid=%s)", uuid)));
		}
	}

	@GetMapping(path = "tweets")
	public ResponseEntity<PaginatedResult<TweetOutput>> searchTweets(@RequestParam String text,
			@RequestParam(defaultValue = "9999-12-31T23:59:59Z") Instant since,
			@RequestParam(required = false) Instant until,
			@RequestParam(defaultValue = "10") int limit,
			UriComponentsBuilder builder) {
		final List<Tweet> tweets = until != null ?
				this.tweetMapper.findByTextContainingUntil(text, until, limit) :
				this.tweetMapper.findByTextContainingSince(text, since, limit);
		final List<TweetOutput> tweetOutputs = tweets.stream()
				.map(TweetOutput::fromTweet)
				.collect(Collectors.toList());
		final URI uri = builder.path("tweets").queryParam("text", text).build().toUri();
		return ResponseEntity.ok(new PaginatedResult<>(tweetOutputs, TweetOutput::getCreatedAt, uri, limit));
	}

	@DeleteMapping(path = "tweets/{uuid}")
	public ResponseEntity<?> deleteTweet(@PathVariable UUID uuid, @RequestAttribute("tweeter") Tweeter tweeter) {
		final Tweet tweet = this.tweetMapper.findByUuid(uuid);
		if (tweet.isOwnedBy(tweeter)) {
			this.tweetMapper.deleteByUuid(uuid);
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(Map.of("error", "Forbidden", "status", 403));
		}
	}

	@PostMapping(path = "tweets")
	public ResponseEntity<TweetOutput> postTweets(@Validated @RequestBody TweetInput input, @RequestAttribute("tweeter") Tweeter tweeter, UriComponentsBuilder builder) {
		final Tweet tweet = new Tweet(this.idGenerator.generateId(), input.getText(), new Tweeter(tweeter.getUsername()), Instant.now(this.clock));
		this.tweetMapper.insert(tweet);
		final TweetOutput output = TweetOutput.fromTweet(tweet);
		final URI location = builder.path("tweets/{uuid}").build(output.getUuid());
		return ResponseEntity.created(location).body(output);
	}

	@GetMapping(path = "tweeters/{username}/tweets")
	public ResponseEntity<?> getTweetsByUsername(@PathVariable String username,
			@RequestParam(defaultValue = "9999-12-31T23:59:59Z") Instant since,
			@RequestParam(required = false) Instant until,
			@RequestParam(defaultValue = "10") int limit,
			UriComponentsBuilder builder) {
		if (this.tweeterMapper.countByUsername(username) == 0L) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("error", "Not Found",
							"status", 404,
							"message", String.format("The given username is not found (username=%s)", username)));
		}
		final List<Tweet> tweets = until != null ?
				this.tweetMapper.findByUsernameUntil(username, until, limit) :
				this.tweetMapper.findByUsernameSince(username, since, limit);
		final List<TweetOutput> tweetOutputs = tweets.stream()
				.map(TweetOutput::fromTweet)
				.collect(Collectors.toList());
		final URI uri = builder.path("tweeters/{username}/tweets").build(username);
		return ResponseEntity.ok(new PaginatedResult<>(tweetOutputs, TweetOutput::getCreatedAt, uri, limit));
	}
}
