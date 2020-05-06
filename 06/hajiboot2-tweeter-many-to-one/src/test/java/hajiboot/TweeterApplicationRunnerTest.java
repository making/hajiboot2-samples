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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(OutputCaptureExtension.class)
public class TweeterApplicationRunnerTest {

    @Test
    void contextLoads(CapturedOutput capture) throws Exception {
        TweetMapper tweetMapper = mock(TweetMapper.class);
        TweeterMapper tweeterMapper = mock(TweeterMapper.class);

        given(tweetMapper.count()).willReturn(2L);
        given(tweetMapper.insert(any())).willReturn(1);
        given(tweeterMapper.countByUsername("making")).willReturn(1L);
        Instant now = Instant.now();
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        given(tweetMapper.findAll())
                .willReturn(Arrays.asList(new Tweet(uuid1, "Demo1", new Tweeter("making", "making@example.com", "password", now), now),
                        new Tweet(uuid2, "Demo2", new Tweeter("making", "making@example.com", "password", now), now)));

        TweeterApplicationRunner tweeterApplicationRunner = new TweeterApplicationRunner(
                tweetMapper, tweeterMapper);
        tweeterApplicationRunner.run(new DefaultApplicationArguments(new String[]{}));
        String output = capture.toString();
        assertThat(output).contains("Number of tweets: 2");
        assertThat(output).contains(
                "Tweet{uuid=" + uuid1 + ", text='Demo1', tweeter=Tweeter{username='making', email='making@example.com'}, createdAt=" + now + "}");
        assertThat(output).contains(
                "Tweet{uuid=" + uuid2 + ", text='Demo2', tweeter=Tweeter{username='making', email='making@example.com'}, createdAt=" + now + "}");
        ;
    }

}