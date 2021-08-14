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
		given(this.tweeterMapper.insert(any()))
				.willReturn(1);

		this.mockMvc.perform(post("/tweeters")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"jdoe\", \"email\":\"jdoe@example.com\", \"password\":\"ppppp\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username").value("jdoe"))
				.andExpect(header().string("Location", "http://localhost/tweeters/jdoe/tweets"));

		final ArgumentCaptor<Tweeter> arg = ArgumentCaptor.forClass(Tweeter.class);
		verify(this.tweeterMapper).insert(arg.capture());
		final Tweeter created = arg.getValue();

		assertThat(created).isNotNull();
		assertThat(created.getUsername()).isEqualTo("jdoe");
		assertThat(created.getEmail()).isEqualTo("jdoe@example.com");
		assertThat(created.getPassword()).isEqualTo("{noop}ppppp");
		assertThat(created.getCreatedAt()).isEqualTo(Instant.parse("2021-08-14T00:00:00Z"));
	}

	@TestConfiguration
	static class AppConfig {
		@Bean
		public Clock clock() {
			return Clock.fixed(Instant.parse("2021-08-14T00:00:00Z"), ZoneId.systemDefault());
		}
	}
}