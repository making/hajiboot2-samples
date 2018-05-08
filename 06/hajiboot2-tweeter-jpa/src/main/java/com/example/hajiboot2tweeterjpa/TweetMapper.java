package com.example.hajiboot2tweeterjpa;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TweetMapper {
	private final EntityManager entityManager;

	public TweetMapper(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public void insert(Tweet tweet) {
		this.entityManager.persist(tweet);
	}

	public long count() {
		return this.entityManager.createQuery("SELECT count(x) FROM Tweet x", Long.class)
				.getSingleResult();
	}

	public List<Tweet> findAll() {
		return this.entityManager.createQuery("SELECT x FROM Tweet x", Tweet.class)
				.getResultList();
	}
}
