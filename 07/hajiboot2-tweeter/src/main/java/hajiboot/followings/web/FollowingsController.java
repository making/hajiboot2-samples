package hajiboot.followings.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowingsController {
    @GetMapping(path = "tweeters/{username}/followings")
    public Object getFollowing(@PathVariable("username") String username) {
        return null;
    }

    @GetMapping(path = "tweeters/{username}/followers")
    public Object getFollowers(@PathVariable("username") String username) {
        return null;
    }
}
