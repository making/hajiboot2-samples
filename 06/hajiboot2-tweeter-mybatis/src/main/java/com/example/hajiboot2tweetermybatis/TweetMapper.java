package com.example.hajiboot2tweetermybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TweetMapper {
	@Insert("INSERT INTO tweets(uuid, text, username, created_at) VALUES (#{uuid}, #{text}, #{username}, #{createdAt})")
	int insert(Tweet tweet);

	@Select("SELECT count(*) FROM tweets")
	long count();

	@Select("SELECT uuid, text, username, created_at FROM tweets")
	List<Tweet> findAll();
}
