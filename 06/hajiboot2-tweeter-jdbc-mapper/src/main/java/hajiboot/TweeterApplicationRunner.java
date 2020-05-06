package hajiboot;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TweeterApplicationRunner implements ApplicationRunner {
	private final TweetMapper tweetMapper;

	public TweeterApplicationRunner(TweetMapper tweetMapper) {
		this.tweetMapper = tweetMapper;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.tweetMapper
				.insert(new Tweet(UUID.randomUUID(), "Demo1", "making", Instant.now()));
		this.tweetMapper
				.insert(new Tweet(UUID.randomUUID(), "Demo2", "making", Instant.now()));
		long count = this.tweetMapper.count();
		System.out.println("Number of tweets: " + count);
		List<Tweet> tweets = this.tweetMapper.findAll();
		tweets.forEach(tweet -> System.out.println(tweet));
	}
}
