package hajiboot.tweeter.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweeterController {
    @PostMapping(path = "tweeters")
    public ResponseEntity<TweeterOutput> postTweeters(@RequestBody TweeterInput input) {
        final TweeterOutput tweeterOutput = new TweeterOutput(input.getUsername());
        return ResponseEntity.ok(tweeterOutput);
    }
}
