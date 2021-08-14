package hajiboot.followings.web;

import java.util.List;
import java.util.stream.Collectors;

import hajiboot.followings.Followings;
import hajiboot.followings.FollowingsMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowingsController {
	private final FollowingsMapper followingsMapper;

	public FollowingsController(FollowingsMapper followingsMapper) {
		this.followingsMapper = followingsMapper;
	}

	@GetMapping(path = "tweeters/{username}/followings")
	public ResponseEntity<List<FollowingOutput>> getFollowingByUsername(@PathVariable("username") String username) {
		final Followings followings = this.followingsMapper.findFollowingsByUsername(username);
		final List<FollowingOutput> followingOutputs = followings.getTweeters().stream()
				.map(FollowingOutput::fromTweeter)
				.collect(Collectors.toList());
		return ResponseEntity.ok(followingOutputs);
	}

	@GetMapping(path = "tweeters/{username}/followers")
	public ResponseEntity<List<FollowingOutput>> getFollowersByUsername(@PathVariable("username") String username) {
		final Followings followers = this.followingsMapper.findFollowersByUsername(username);
		final List<FollowingOutput> followingOutputs = followers.getTweeters().stream()
				.map(FollowingOutput::fromTweeter)
				.collect(Collectors.toList());
		return ResponseEntity.ok(followingOutputs);
	}

	@PutMapping(path = "followings")
	public ResponseEntity<FollowingOutput> putFollowings(@Validated @RequestBody FollowingInput input) {
		this.followingsMapper.insert(input.getFollower(), input.getFollowee());
		final FollowingOutput output = new FollowingOutput(input.getFollowee());
		return ResponseEntity.ok(output);
	}

	@DeleteMapping(path = "followings")
	public ResponseEntity<Void> deleteFollowings(@RequestBody FollowingInput input) {
		this.followingsMapper.delete(input.getFollower(), input.getFollowee());
		return ResponseEntity.noContent().build();
	}
}
