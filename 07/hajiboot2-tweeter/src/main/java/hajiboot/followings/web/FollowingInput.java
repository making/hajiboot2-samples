package hajiboot.followings.web;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class FollowingInput {
	@NotBlank
	private final String followee;

	@JsonCreator
	public FollowingInput(@JsonProperty("followee") String followee) {
		this.followee = followee;
	}

	public String getFollowee() {
		return followee;
	}
}
