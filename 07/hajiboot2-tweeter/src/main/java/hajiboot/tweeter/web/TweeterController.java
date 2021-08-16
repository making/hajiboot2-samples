package hajiboot.tweeter.web;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolationsException;
import am.ik.yavi.core.Validator;
import hajiboot.tweeter.Tweeter;
import hajiboot.tweeter.TweeterMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class TweeterController {
	private final TweeterMapper tweeterMapper;

	private final Clock clock;

	private final Validator<TweeterInput> tweeterInputValidator;

	public TweeterController(TweeterMapper tweeterMapper, Clock clock) {
		this.tweeterMapper = tweeterMapper;
		this.clock = clock;
		this.tweeterInputValidator = ValidatorBuilder.<TweeterInput>of()
				.constraint(TweeterInput::getUsername, "username", c -> c.notBlank()
						.pattern("[a-zA-Z0-9_]+")
						.lessThanOrEqual(128)
						.predicate(tweeterMapper::isUnusedUsername, "username.exists", "The given {0} is already used"))
				.constraint(TweeterInput::getEmail, "email", c -> c.notBlank()
						.email()
						.lessThanOrEqual(128)
						.predicate(tweeterMapper::isUnusedEmail, "email.exists", "The given {0} is already used"))
				.constraint(TweeterInput::getPassword, "password", c -> c.notBlank()
						.greaterThanOrEqual(8)
						.lessThanOrEqual(255)
						.password(policy -> policy.numbers().lowercase().uppercase().build()))
				.build();
	}

	@PostMapping(path = "tweeters")
	public ResponseEntity<TweeterOutput> postTweeters(@RequestBody TweeterInput input, UriComponentsBuilder builder) {
		this.tweeterInputValidator.validate(input).throwIfInvalid(ConstraintViolationsException::new);
		final String encodedPassword = "{noop}" + input.getPassword(); // TODO Password Encode
		final Tweeter tweeter = new Tweeter(input.getUsername(), input.getEmail(), encodedPassword, Instant.now(this.clock));
		this.tweeterMapper.insert(tweeter);
		final TweeterOutput tweeterOutput = new TweeterOutput(tweeter.getUsername());
		final URI location = builder.path("tweeters/{username}/tweets").build(tweeter.getUsername());
		return ResponseEntity.created(location).body(tweeterOutput);
	}
}
