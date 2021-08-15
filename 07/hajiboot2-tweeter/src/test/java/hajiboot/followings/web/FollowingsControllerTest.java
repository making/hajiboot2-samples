package hajiboot.followings.web;

import java.util.List;

import hajiboot.followings.Followings;
import hajiboot.followings.FollowingsMapper;
import hajiboot.tweeter.Tweeter;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

	@Test
	void getFollowingByUsername() throws Exception {
		given(this.followingsMapper.findFollowingsByUsername("test"))
				.willReturn(new Followings("test", List.of(new Tweeter("demo3"), new Tweeter("demo2"), new Tweeter("demo1"))));

		this.mockMvc.perform(get("/tweeters/test/followings"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[0].username").value("demo3"))
				.andExpect(jsonPath("$[1].username").value("demo2"))
				.andExpect(jsonPath("$[2].username").value("demo1"));
	}

	@Test
	void getFollowersByUsername() throws Exception {
		given(this.followingsMapper.findFollowersByUsername("test"))
				.willReturn(new Followings("test", List.of(new Tweeter("demo3"), new Tweeter("demo2"), new Tweeter("demo1"))));

		this.mockMvc.perform(get("/tweeters/test/followers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[0].username").value("demo3"))
				.andExpect(jsonPath("$[1].username").value("demo2"))
				.andExpect(jsonPath("$[2].username").value("demo1"));
	}

	@Test
	void putFollowings() throws Exception {
		given(this.followingsMapper.insert("demo1", "demo2")).willReturn(1);
		this.mockMvc.perform(put("/followings")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"follower\":\"demo1\", \"followee\":\"demo2\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("demo2"));
		verify(this.followingsMapper).insert("demo1", "demo2");
	}

	@Test
	void putFollowingsInvalid() throws Exception {
		this.mockMvc.perform(put("/followings")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"follower\":\"\", \"followee\":\"\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.details.length()").value(2))
				.andExpect(jsonPath("$.details[*].followee").value("must not be blank"))
				.andExpect(jsonPath("$.details[*].follower").value("must not be blank"));
	}

	@Test
	void deleteFollowings() throws Exception {
		given(this.followingsMapper.delete("demo1", "demo2")).willReturn(1);
		this.mockMvc.perform(delete("/followings")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"follower\":\"demo1\", \"followee\":\"demo2\"}"))
				.andExpect(status().isNoContent());
		verify(this.followingsMapper).delete("demo1", "demo2");
	}
}