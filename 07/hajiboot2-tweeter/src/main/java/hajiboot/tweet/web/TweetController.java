package hajiboot.tweet.web;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import hajiboot.tweet.Tweet;
import hajiboot.tweet.TweetMapper;
import hajiboot.tweeter.Tweeter;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.IdGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class TweetController {
	private final TweetMapper tweetMapper;

	private final IdGenerator idGenerator;

	private final Clock clock;

	public TweetController(TweetMapper tweetMapper, IdGenerator idGenerator, Clock clock) {
		this.tweetMapper = tweetMapper;
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
		catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given uuid is not found (uuid=%s)", uuid), e);
		}
	}

	@DeleteMapping(path = "tweets/{uuid}")
	public ResponseEntity<Void> deleteTweet(@PathVariable UUID uuid) {
		this.tweetMapper.deleteByUuid(uuid);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(path = "tweets")
	public ResponseEntity<TweetOutput> postTweets(@Validated @RequestBody TweetInput input, UriComponentsBuilder builder) {
		final Tweet tweet = new Tweet(this.idGenerator.generateId(), input.getText(), new Tweeter(input.getUsername()), Instant.now(this.clock));
		this.tweetMapper.insert(tweet);
		final TweetOutput output = TweetOutput.fromTweet(tweet);
		final URI location = builder.path("tweets/{uuid}").build(output.getUuid());
		return ResponseEntity.created(location).body(output);
	}

	@GetMapping(path = "tweeters/{username}/tweets")
	public ResponseEntity<List<TweetOutput>> getTweetsByUsername(@PathVariable String username) {
		final List<Tweet> tweets = this.tweetMapper.findByUsername(username);
		final List<TweetOutput> tweetOutputs = tweets.stream()
				.map(TweetOutput::fromTweet)
				.collect(Collectors.toList());
		return ResponseEntity.ok(tweetOutputs);
	}
}
