package hajiboot.followings.web;

import java.time.Instant;
import java.util.List;

import hajiboot.followings.Followings;
import hajiboot.followings.FollowingsMapper;
import hajiboot.tweeter.Tweeter;
import hajiboot.tweeter.TweeterMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FollowingsController.class)
class FollowingsControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	FollowingsMapper followingsMapper;

	@MockBean
	TweeterMapper tweeterMapper;

	@Test
	void getFollowingByUsername() throws Exception {
		given(this.followingsMapper.findFollowingsByUsername("test"))
				.willReturn(new Followings("test", List.of(new Tweeter("foo3"), new Tweeter("foo2"), new Tweeter("foo1"))));

		this.mockMvc.perform(get("/tweeters/test/followings"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.length()").value(3))
				.andExpect(jsonPath("$.data[0].username").value("foo3"))
				.andExpect(jsonPath("$.data[1].username").value("foo2"))
				.andExpect(jsonPath("$.data[2].username").value("foo1"));
	}

	@Test
	void getFollowersByUsername() throws Exception {
		given(this.followingsMapper.findFollowersByUsername("test"))
				.willReturn(new Followings("test", List.of(new Tweeter("foo3"), new Tweeter("foo2"), new Tweeter("foo1"))));

		this.mockMvc.perform(get("/tweeters/test/followers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.length()").value(3))
				.andExpect(jsonPath("$.data[0].username").value("foo3"))
				.andExpect(jsonPath("$.data[1].username").value("foo2"))
				.andExpect(jsonPath("$.data[2].username").value("foo1"));
	}

	@Test
	void putFollowings() throws Exception {
		given(this.followingsMapper.insert("foo1", "foo2")).willReturn(1);
		given(this.tweeterMapper.findByUsername("foo1")).willReturn(new Tweeter("foo1", "foo1@example.com", "{noop}password", Instant.now()));
		this.mockMvc.perform(put("/followings")
						.header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("foo1", "password", UTF_8))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"followee\":\"foo2\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("foo2"));
		verify(this.followingsMapper).insert("foo1", "foo2");
	}

	@Test
	void putFollowingsInvalid() throws Exception {
		given(this.tweeterMapper.findByUsername("foo1")).willReturn(new Tweeter("foo1", "foo1@example.com", "{noop}password", Instant.now()));
		this.mockMvc.perform(put("/followings")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("foo1", "password", UTF_8))
						.content("{\"followee\":\"\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.details.length()").value(1))
				.andExpect(jsonPath("$.details[0].name").value("followee"))
				.andExpect(jsonPath("$.details[0].message").value("\"followee\" must not be blank"));
	}

	@Test
	void deleteFollowings() throws Exception {
		given(this.followingsMapper.delete("foo1", "foo2")).willReturn(1);
		given(this.tweeterMapper.findByUsername("foo")).willReturn(new Tweeter("foo1", "foo1@example.com", "{noop}password", Instant.now()));
		this.mockMvc.perform(delete("/followings")
						.header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("foo", "password", UTF_8))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"followee\":\"foo2\"}"))
				.andExpect(status().isNoContent());
		verify(this.followingsMapper).delete("foo1", "foo2");
	}
}