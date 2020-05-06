INSERT INTO followings (follower, followee, created_at) VALUES ('foo1', 'foo2', now() + 1);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo1', 'foo3', now() + 2);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo1', 'foo4', now() + 3);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo2', 'foo1', now() + 4);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo4', 'foo1', now() + 5);
INSERT INTO followings (follower, followee, created_at) VALUES ('foo3', 'foo2', now() + 6);