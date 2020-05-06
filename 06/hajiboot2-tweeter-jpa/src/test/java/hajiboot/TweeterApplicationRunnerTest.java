package hajiboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(OutputCaptureExtension.class)
public class TweeterApplicationRunnerTest {

	@Test
	void contextLoads(CapturedOutput capture) throws Exception {
		TweetMapper tweetMapper = mock(TweetMapper.class);

		given(tweetMapper.count()).willReturn(2L);
		// (1)
		Instant now = Instant.now();
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		given(tweetMapper.findAll())
				.willReturn(Arrays.asList(new Tweet(uuid1, "test1", "making", now),
						new Tweet(uuid2, "test2", "making", now)));

		TweeterApplicationRunner tweeterApplicationRunner = new TweeterApplicationRunner(
				tweetMapper);
		tweeterApplicationRunner.run(new DefaultApplicationArguments(new String[] {}));
		String output = capture.toString();
		assertThat(output).contains("Number of tweets: 2");
		assertThat(output).contains("Tweet{uuid=" + uuid1
				+ ", text='test1', username='making', createdAt=" + now + "}");
		assertThat(output).contains("Tweet{uuid=" + uuid2
				+ ", text='test2', username='making', createdAt=" + now + "}");
	}
}