package hajiboot;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import hajiboot.tweet.Tweet;
import hajiboot.tweet.TweetMapper;
import hajiboot.tweeter.Tweeter;
import hajiboot.tweeter.TweeterMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TweeterApplicationRunner implements ApplicationRunner {
	private final TweetMapper tweetMapper;
	private final TweeterMapper tweeterMapper;

	public TweeterApplicationRunner(TweetMapper tweetMapper,
                                    TweeterMapper tweeterMapper) {
		this.tweetMapper = tweetMapper;
		this.tweeterMapper = tweeterMapper;
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		Tweeter tweeter = new Tweeter("making", "making@example.com", "password",
				Instant.now());
		if (this.tweeterMapper.countByUsername(tweeter.getUsername()) == 0) { // (1)
			this.tweeterMapper.insert(tweeter);
		}
		this.tweetMapper
				.insert(new Tweet(UUID.randomUUID(), "Demo1", tweeter, Instant.now()));
		this.tweetMapper
				.insert(new Tweet(UUID.randomUUID(), "Demo2", tweeter, Instant.now()));
		long count = this.tweetMapper.count();
		System.out.println("Number of tweets: " + count);
		List<Tweet> tweets = this.tweetMapper.findAll();
		tweets.forEach(tweet -> System.out.println(tweet));
	}
}