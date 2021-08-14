package hajiboot.tweet.web;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import hajiboot.tweet.Tweet;
import hajiboot.tweet.TweetMapper;
import hajiboot.tweeter.Tweeter;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.IdGenerator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TweetController.class)
public class TweetControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	TweetMapper tweetMapper;

	@MockBean
	IdGenerator idGenerator;

	Tweet tweet1 = new Tweet(UUID.randomUUID(), "Hello 1!", new Tweeter("test"), Instant.now());

	Tweet tweet2 = new Tweet(UUID.randomUUID(), "Hello 2!", new Tweeter("test"), Instant.now());

	Tweet tweet3 = new Tweet(UUID.randomUUID(), "Hello 3!", new Tweeter("test"), Instant.now());


	@Test
	void getTweet() throws Exception {
		given(this.tweetMapper.findByUuid(this.tweet1.getUuid())).willReturn(this.tweet1);
		this.mockMvc.perform(get("/tweets/{uuid}", this.tweet1.getUuid()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.uuid").value(this.tweet1.getUuid().toString()))
				.andExpect(jsonPath("$.text").value("Hello 1!"))
				.andExpect(jsonPath("$.username").value("test"))
				.andExpect(jsonPath("$.createdAt").value(this.tweet1.getCreatedAt().toString()));
	}

	@Test
	void deleteTweet() throws Exception {
		given(this.tweetMapper.deleteByUuid(this.tweet1.getUuid())).willReturn(1);
		this.mockMvc.perform(delete("/tweets/{uuid}", this.tweet1.getUuid()))
				.andExpect(status().isNoContent());
		verify(this.tweetMapper).deleteByUuid(this.tweet1.getUuid());
	}

	@Test
	void postTweets() throws Exception {
		final UUID uuid = UUID.randomUUID();
		given(this.tweetMapper.insert(any())).willReturn(1);
		given(this.idGenerator.generateId()).willReturn(uuid);
		this.mockMvc.perform(post("/tweets")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"test\", \"text\":\"Hello Tweeter!\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.uuid").value(uuid.toString()))
				.andExpect(jsonPath("$.username").value("test"))
				.andExpect(jsonPath("$.text").value("Hello Tweeter!"))
				.andExpect(jsonPath("$.createdAt").value("2021-08-14T00:00:00Z"))
				.andExpect(header().string("Location", "http://localhost/tweets/" + uuid));
		verify(this.tweetMapper).insert(any());
	}

	@Test
	void postTweetsInvalid() throws Exception {
		this.mockMvc.perform(post("/tweets")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"\", \"text\":\"\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.details.length()").value(2))
				.andExpect(jsonPath("$.details[0].username").value("must not be blank"))
				.andExpect(jsonPath("$.details[1].text").value("must not be blank"));
	}

	@Test
	void getTweetsByUsername() throws Exception {
		given(this.tweetMapper.findByUsername("test")).willReturn(List.of(this.tweet3, this.tweet2, this.tweet1));
		this.mockMvc.perform(get("/tweeters/test/tweets"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[0].uuid").value(this.tweet3.getUuid().toString()))
				.andExpect(jsonPath("$[0].text").value("Hello 3!"))
				.andExpect(jsonPath("$[0].username").value("test"))
				.andExpect(jsonPath("$[0].createdAt").value(this.tweet3.getCreatedAt().toString()))
				.andExpect(jsonPath("$[1].uuid").value(this.tweet2.getUuid().toString()))
				.andExpect(jsonPath("$[1].text").value("Hello 2!"))
				.andExpect(jsonPath("$[1].username").value("test"))
				.andExpect(jsonPath("$[1].createdAt").value(this.tweet2.getCreatedAt().toString()))
				.andExpect(jsonPath("$[2].uuid").value(this.tweet1.getUuid().toString()))
				.andExpect(jsonPath("$[2].text").value("Hello 1!"))
				.andExpect(jsonPath("$[2].username").value("test"))
				.andExpect(jsonPath("$[2].createdAt").value(this.tweet1.getCreatedAt().toString()));
	}

	@TestConfiguration
	static class AppConfig {
		@Bean
		public Clock clock() {
			return Clock.fixed(Instant.parse("2021-08-14T00:00:00Z"), ZoneId.systemDefault());
		}
	}
}
