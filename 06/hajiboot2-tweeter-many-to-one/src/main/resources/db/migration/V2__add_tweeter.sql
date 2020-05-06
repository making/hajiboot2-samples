CREATE TABLE tweeters (
  username   VARCHAR(128) PRIMARY KEY,
  email      VARCHAR(128) UNIQUE,
  password   VARCHAR(255),
  created_at TIMESTAMP
);

ALTER TABLE tweets
  ADD CONSTRAINT fk_tweets_username FOREIGN KEY (username) REFERENCES tweeters (username);