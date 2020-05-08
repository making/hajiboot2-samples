package hajiboot.followings.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class FollowingsController {
    @GetMapping(path = "tweeters/{username}/followings")
    public ResponseEntity<List<FollowingOutput>> getFollowingByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(Arrays.asList(new FollowingOutput("foo2"), new FollowingOutput("foo3")));
    }

    @GetMapping(path = "tweeters/{username}/followers")
    public ResponseEntity<List<FollowingOutput>> getFollowersByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(Arrays.asList(new FollowingOutput("foo3"), new FollowingOutput("foo4")));
    }

    @GetMapping(path = "followings")
    public ResponseEntity<List<FollowingOutput>> getFollowings() {
        return ResponseEntity.ok(Arrays.asList(new FollowingOutput("foo2"), new FollowingOutput("foo3")));
    }

    @PostMapping(path = "followings")
    public ResponseEntity<FollowingOutput> postFollowings(@RequestBody FollowingInput input) {
        final FollowingOutput output = new FollowingOutput(input.getUsername());
        return ResponseEntity.ok(output);
    }
}
