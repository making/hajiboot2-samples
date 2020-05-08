package hajiboot.tweet.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TweetController {
    private final Map<UUID, TweetOutput> mockTweets;

    public TweetController() {
        this.mockTweets = Stream.of(
                new TweetOutput(UUID.fromString("127f679c-b4e3-450b-bb3f-b5b7f32fc6a0"), "Hello1", "demo", Instant.parse("2020-05-07T13:36:57.366Z")),
                new TweetOutput(UUID.fromString("5b74be91-5c13-4b0e-908d-f3fa9aa70b26"), "Hello2", "demo", Instant.parse("2020-05-07T13:37:57.366Z")),
                new TweetOutput(UUID.fromString("2bf80fec-ea55-4d05-b3ea-392bcec7483e"), "Hi!", "foo", Instant.parse("2020-05-07T13:38:57.366Z")),
                new TweetOutput(UUID.fromString("52b6e221-77f9-4e1f-bdf7-2dcf09c44b81"), "Hello3", "demo", Instant.parse("2020-05-07T13:39:57.366Z")))
                .collect(Collectors.toConcurrentMap(TweetOutput::getUuid, Function.identity()));
    }

    @GetMapping(path = "tweets/{uuid}")
    public ResponseEntity<TweetOutput> getTweet(@PathVariable("uuid") UUID uuid) {
        final TweetOutput output = this.mockTweets.get(uuid);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping(path = "tweets/{uuid}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("uuid") UUID uuid) {
        this.mockTweets.remove(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "tweets")
    public ResponseEntity<TweetOutput> postTweets(@RequestBody TweetInput input, UriComponentsBuilder builder) {
        final TweetOutput output = new TweetOutput(UUID.randomUUID(), input.getText(), "demo", Instant.now());
        this.mockTweets.put(output.getUuid(), output);
        final URI location = builder.path("tweets/{uuid}").build(output.getUuid());
        return ResponseEntity.created(location).body(output);
    }

    @GetMapping(path = "tweets")
    public ResponseEntity<List<TweetOutput>> getTweets() {
        final List<TweetOutput> outputs = mockTweets.values().stream()
                .sorted(Comparator.comparing(TweetOutput::getCreatedAt).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok(outputs);
    }

    @GetMapping(path = "tweeters/{username}/tweets")
    public ResponseEntity<List<TweetOutput>> getTweetsByUsername(@PathVariable("username") String username) {
        final List<TweetOutput> outputs = this.mockTweets.values().stream()
                .filter(t -> Objects.equals(t.getUsername(), username))
                .sorted(Comparator.comparing(TweetOutput::getCreatedAt).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok(outputs);
    }
}
