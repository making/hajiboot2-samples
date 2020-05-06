package hajiboot;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TweetMapper {
	int insert(Tweet tweet);

	long count();

	List<Tweet> findAll();
}
