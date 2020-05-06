package hajiboot;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, UUID> {
}
