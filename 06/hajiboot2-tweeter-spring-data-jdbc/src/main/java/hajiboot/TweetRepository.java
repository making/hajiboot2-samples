package hajiboot;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TweetRepository extends CrudRepository<Tweet, UUID> {
}
