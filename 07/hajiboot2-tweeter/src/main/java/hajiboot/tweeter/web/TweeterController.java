package hajiboot.tweeter.web;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;

import hajiboot.tweeter.Tweeter;
import hajiboot.tweeter.TweeterMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class TweeterController {
	private final TweeterMapper tweeterMapper;

	private final Clock clock;

	public TweeterController(TweeterMapper tweeterMapper, Clock clock) {
		this.tweeterMapper = tweeterMapper;
		this.clock = clock;
	}

	@PostMapping(path = "tweeters")
	public ResponseEntity<TweeterOutput> postTweeters(@Validated @RequestBody TweeterInput input, UriComponentsBuilder builder) {
		final String encodedPassword = "{noop}" + input.getPassword(); // TODO Password Encode
		final Tweeter tweeter = new Tweeter(input.getUsername(), input.getEmail(), encodedPassword, Instant.now(this.clock));
		this.tweeterMapper.insert(tweeter);
		final TweeterOutput tweeterOutput = new TweeterOutput(tweeter.getUsername());
		final URI location = builder.path("tweeters/{username}/tweets").build(tweeter.getUsername());
		return ResponseEntity.created(location).body(tweeterOutput);
	}
}
