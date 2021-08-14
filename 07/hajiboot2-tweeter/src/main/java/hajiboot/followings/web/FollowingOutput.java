package hajiboot.followings.web;

import hajiboot.tweeter.Tweeter;

class FollowingOutput {
	private final String username;

	public FollowingOutput(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public static FollowingOutput fromTweeter(Tweeter tweeter) {
		return new FollowingOutput(tweeter.getUsername());
	}
}
