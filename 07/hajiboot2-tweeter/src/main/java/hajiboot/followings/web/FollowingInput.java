package hajiboot.followings.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class FollowingInput {
	private final String followee;

	@JsonCreator
	public FollowingInput(@JsonProperty("followee") String followee) {
		this.followee = followee;
	}

	public String getFollowee() {
		return followee;
	}
}
