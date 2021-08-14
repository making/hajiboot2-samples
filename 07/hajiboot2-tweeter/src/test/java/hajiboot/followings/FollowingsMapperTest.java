package hajiboot.followings;

import hajiboot.tweeter.Tweeter;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = {
		"classpath:/delete-test-following.sql",
		"classpath:/delete-test-tweeter.sql",
		"classpath:/insert-test-tweeter.sql",
		"classpath:/insert-test-following.sql" })
public class FollowingsMapperTest {
	private final FollowingsMapper followingsMapper;

	public FollowingsMapperTest(JdbcTemplate jdbcTemplate) {
		this.followingsMapper = new FollowingsMapper(jdbcTemplate);
	}

	@Test
	void findFollowingsByUsername() {
		final Followings followings = this.followingsMapper.findFollowingsByUsername("foo");
		assertThat(followings.getUsername()).isEqualTo("foo");
		assertThat(followings.getTweeters()).containsExactly(new Tweeter("foo4"), new Tweeter("foo3"), new Tweeter("foo2"));
	}

	@Test
	void findFollowersByUsername() {
		final Followings followings = this.followingsMapper.findFollowersByUsername("foo");
		assertThat(followings.getUsername()).isEqualTo("foo");
		assertThat(followings.getTweeters()).containsExactly(new Tweeter("foo4"), new Tweeter("foo2"));
	}

	@Test
	void insert() {
		final int insert = this.followingsMapper.insert("foo2", "foo3");
		assertThat(insert).isEqualTo(1);
		final Followings followings = this.followingsMapper.findFollowingsByUsername("foo2");
		assertThat(followings.getTweeters()).containsExactly(new Tweeter("foo3"), new Tweeter("foo"));
	}

	@Test
	void delete() {
		final int delete = this.followingsMapper.delete("foo", "foo3");
		assertThat(delete).isEqualTo(1);
		final Followings followings = this.followingsMapper.findFollowingsByUsername("foo");
		assertThat(followings.getTweeters()).containsExactly(new Tweeter("foo4"), new Tweeter("foo2"));
	}
}