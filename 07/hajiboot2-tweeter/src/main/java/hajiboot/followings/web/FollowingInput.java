package hajiboot.followings.web;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class FollowingInput {
	@NotBlank
	private final String follower;

	@NotBlank
	private final String followee;

	@JsonCreator
	public FollowingInput(@JsonProperty("follower") String follower  /* TODO from Authentication */,
			@JsonProperty("followee") String followee) {
		this.follower = follower;
		this.followee = followee;
	}

	public String getFollower() {
		return follower;
	}

	public String getFollowee() {
		return followee;
	}
}
