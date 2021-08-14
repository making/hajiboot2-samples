package hajiboot.followings.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class FollowingInput {
	private final String follower;

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
