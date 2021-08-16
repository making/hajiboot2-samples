package hajiboot;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaginatedResultTest {

	@Test
	void paginatedResult() {
		final List<Instant> instants = List.of(Instant.parse("2021-08-14T03:00:00Z"),
				Instant.parse("2021-08-14T02:00:00Z"),
				Instant.parse("2021-08-14T01:00:00Z"),
				Instant.parse("2021-08-14T00:00:00Z"));
		final URI uri = URI.create("http://localhost:8080/tweeters/foo/tweets");
		final PaginatedResult<Instant> paginatedResult = new PaginatedResult<>(instants, Function.identity(), uri, 10);
		assertThat(paginatedResult.getData()).isEqualTo(instants);
		assertThat(paginatedResult.getPaging().getNext()).isEqualTo(URI.create("http://localhost:8080/tweeters/foo/tweets?limit=10&until=2021-08-14T03:00:00Z"));
		assertThat(paginatedResult.getPaging().getPrevious()).isEqualTo(URI.create("http://localhost:8080/tweeters/foo/tweets?limit=10&since=2021-08-14T00:00:00Z"));
	}

	@Test
	void paginatedResultReplace() {
		final List<Instant> instants = List.of(Instant.parse("2021-08-14T03:00:00Z"),
				Instant.parse("2021-08-14T02:00:00Z"),
				Instant.parse("2021-08-14T01:00:00Z"),
				Instant.parse("2021-08-14T00:00:00Z"));
		final URI uri = URI.create("http://localhost:8080/tweeters/foo/tweets?limit=20&until=2021-08-14T01:00:00Z");
		final PaginatedResult<Instant> paginatedResult = new PaginatedResult<>(instants, Function.identity(), uri, 10);
		assertThat(paginatedResult.getData()).isEqualTo(instants);
		assertThat(paginatedResult.getPaging().getNext()).isEqualTo(URI.create("http://localhost:8080/tweeters/foo/tweets?limit=10&until=2021-08-14T03:00:00Z"));
		assertThat(paginatedResult.getPaging().getPrevious()).isEqualTo(URI.create("http://localhost:8080/tweeters/foo/tweets?limit=10&since=2021-08-14T00:00:00Z"));
	}
}