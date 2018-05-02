package com.example.hajiboot2tweeterjdbc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TweeterApplicationRunner implements ApplicationRunner {
	private final TweetMapper tweeterMapper;

	public TweeterApplicationRunner(TweetMapper tweeterMapper) {
		this.tweeterMapper = tweeterMapper;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.tweeterMapper
				.insert(new Tweet(UUID.randomUUID(), "Demo1", "making", Instant.now()));
		this.tweeterMapper
				.insert(new Tweet(UUID.randomUUID(), "Demo2", "making", Instant.now()));
		long count = this.tweeterMapper.count();
		System.out.println("Number of tweets: " + count);
		List<Tweet> tweets = this.tweeterMapper.findAll();
		tweets.forEach(tweet -> System.out.println(tweet));
	}
}
