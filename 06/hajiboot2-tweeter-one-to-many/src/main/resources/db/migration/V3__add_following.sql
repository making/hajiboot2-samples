CREATE TABLE followings
(
    follower   VARCHAR(128),
    followee   VARCHAR(128),
    created_at TIMESTAMP,
    PRIMARY KEY (follower, followee)
);

ALTER TABLE followings
    ADD CONSTRAINT fk_followings_follower FOREIGN KEY (follower) REFERENCES tweeters (username);
ALTER TABLE followings
    ADD CONSTRAINT fk_followings_followee FOREIGN KEY (followee) REFERENCES tweeters (username);