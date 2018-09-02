package com.example.hajiboot2tweeterspringdatajdbc;

import org.springframework.context.event.EventListener;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class TweetEventListener {
	@EventListener
	public void beforeSaveTweet(BeforeSaveEvent event) {
		Object entity = event.getEntity();
		if (entity instanceof Tweet) {
			Tweet tweet = (Tweet) entity;
			if (tweet.getUuid() == null) {
				tweet.generateUuid();
			}
		}
	}
}
