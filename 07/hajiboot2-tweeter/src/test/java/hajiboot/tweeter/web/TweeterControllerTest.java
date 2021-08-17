package hajiboot.tweeter.web;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import hajiboot.tweeter.Tweeter;
import hajiboot.tweeter.TweeterMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TweeterController.class)
public class TweeterControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	TweeterMapper tweeterMapper;

	@Test
	void postTweeters() throws Exception {
		given(this.tweeterMapper.insert(any())).willReturn(1);
		given(this.tweeterMapper.isUnusedEmail(any())).willReturn(true);
		given(this.tweeterMapper.isUnusedUsername(any())).willReturn(true);

		this.mockMvc.perform(post("/tweeters")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"foo\", \"email\":\"foo@example.com\", \"password\":\"PassWord123\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username").value("foo"))
				.andExpect(header().string("Location", "http://localhost/tweeters/foo/tweets"));

		final ArgumentCaptor<Tweeter> arg = ArgumentCaptor.forClass(Tweeter.class);
		verify(this.tweeterMapper).insert(arg.capture());
		final Tweeter created = arg.getValue();

		assertThat(created).isNotNull();
		assertThat(created.getUsername()).isEqualTo("foo");
		assertThat(created.getEmail()).isEqualTo("foo@example.com");
		assertThat(created.getPassword()).isEqualTo("{noop}PassWord123");
		assertThat(created.getCreatedAt()).isEqualTo(Instant.parse("2021-08-14T00:00:00Z"));
	}

	@Test
	void postTweetersInvalid() throws Exception {
		given(this.tweeterMapper.isUnusedUsername(any())).willReturn(true);
		given(this.tweeterMapper.isUnusedEmail(any())).willReturn(true);
		this.mockMvc.perform(post("/tweeters")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"\", \"email\":\"aa\", \"password\":\"password\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.details.username.length()").value(2))
				.andExpect(jsonPath("$.details.username[0]").value("\"username\" must not be blank"))
				.andExpect(jsonPath("$.details.username[1]").value("\"username\" must match [a-zA-Z0-9_]+"))
				.andExpect(jsonPath("$.details.email.length()").value(1))
				.andExpect(jsonPath("$.details.email[0]").value("\"email\" must be a valid email address"))
				.andExpect(jsonPath("$.details.password.length()").value(2))
				.andExpect(jsonPath("$.details.password[0]").value("\"password\" must meet Numbers policy"))
				.andExpect(jsonPath("$.details.password[1]").value("\"password\" must meet Uppercase policy"));
	}

	@Test
	void postTweetersIsUsed() throws Exception {
		given(this.tweeterMapper.isUnusedUsername(any())).willReturn(false);
		given(this.tweeterMapper.isUnusedEmail(any())).willReturn(false);
		this.mockMvc.perform(post("/tweeters")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"foo\", \"email\":\"foo@example.com\", \"password\":\"PassWord123\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.details.username.length()").value(1))
				.andExpect(jsonPath("$.details.username[0]").value("The given username is already used"))
				.andExpect(jsonPath("$.details.email.length()").value(1))
				.andExpect(jsonPath("$.details.email[0]").value("The given email is already used"));
	}

	@TestConfiguration
	static class AppConfig {
		@Bean
		public Clock clock() {
			return Clock.fixed(Instant.parse("2021-08-14T00:00:00Z"), ZoneId.systemDefault());
		}
	}
}