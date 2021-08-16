package hajiboot.followings.web;

import java.util.List;
import java.util.stream.Collectors;

import hajiboot.ArrayResult;
import hajiboot.followings.Followings;
import hajiboot.followings.FollowingsMapper;
import hajiboot.tweeter.Tweeter;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowingsController {
	private final FollowingsMapper followingsMapper;

	public FollowingsController(FollowingsMapper followingsMapper) {
		this.followingsMapper = followingsMapper;
	}

	@GetMapping(path = "tweeters/{username}/followings")
	public ResponseEntity<ArrayResult<FollowingOutput>> getFollowingByUsername(@PathVariable("username") String username) {
		final Followings followings = this.followingsMapper.findFollowingsByUsername(username);
		final List<FollowingOutput> followingOutputs = followings.getTweeters().stream()
				.map(FollowingOutput::fromTweeter)
				.collect(Collectors.toList());
		return ResponseEntity.ok(new ArrayResult<>(followingOutputs));
	}

	@GetMapping(path = "tweeters/{username}/followers")
	public ResponseEntity<ArrayResult<FollowingOutput>> getFollowersByUsername(@PathVariable("username") String username) {
		final Followings followers = this.followingsMapper.findFollowersByUsername(username);
		final List<FollowingOutput> followingOutputs = followers.getTweeters().stream()
				.map(FollowingOutput::fromTweeter)
				.collect(Collectors.toList());
		return ResponseEntity.ok(new ArrayResult<>(followingOutputs));
	}

	@PutMapping(path = "followings")
	public ResponseEntity<FollowingOutput> putFollowings(@Validated @RequestBody FollowingInput input, @RequestAttribute("tweeter") Tweeter tweeter) {
		this.followingsMapper.insert(tweeter.getUsername(), input.getFollowee());
		final FollowingOutput output = new FollowingOutput(input.getFollowee());
		return ResponseEntity.ok(output);
	}

	@DeleteMapping(path = "followings")
	public ResponseEntity<Void> deleteFollowings(@RequestBody FollowingInput input, @RequestAttribute("tweeter") Tweeter tweeter) {
		this.followingsMapper.delete(tweeter.getUsername(), input.getFollowee());
		return ResponseEntity.noContent().build();
	}
}
