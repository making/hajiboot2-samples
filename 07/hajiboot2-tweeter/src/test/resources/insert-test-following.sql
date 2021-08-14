INSERT INTO followings (follower, followee, created_at) VALUES ('foo', 'foo2', now() - 10);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo', 'foo3', now() - 9);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo', 'foo4', now() - 8);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo2', 'foo', now() - 7);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo4', 'foo', now() - 6);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo3', 'foo2', now() - 5);