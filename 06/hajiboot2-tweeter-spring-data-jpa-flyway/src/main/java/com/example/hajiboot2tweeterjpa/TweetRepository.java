package com.example.hajiboot2tweeterjpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, UUID> {
}
