package hajiboot;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * https://developers.facebook.com/docs/graph-api/results#time
 * @param <T>
 */
public class PaginatedResult<T> {
	private final List<T> data;

	private final Paging paging;

	public PaginatedResult(List<T> data, Function<T, Instant> toInstant, URI uri, int limit) {
		this.data = data;
		this.paging = Paging.from(data, toInstant, uri, limit);
	}

	public List<T> getData() {
		return data;
	}

	public Paging getPaging() {
		return paging;
	}

	public static class Paging {
		private final URI previous;

		private final URI next;

		public static <T> Paging from(List<T> data, Function<T, Instant> toInstant, URI uri, int limit) {
			final T latest = CollectionUtils.firstElement(data);
			final T oldest = CollectionUtils.lastElement(data);
			final URI previous = oldest == null ? null : UriComponentsBuilder.fromUri(uri)
					.replaceQueryParam("limit", limit)
					.replaceQueryParam("since", toInstant.apply(oldest))
					.replaceQueryParam("until")
					.build()
					.encode()
					.toUri();
			final URI next = latest == null ? null : UriComponentsBuilder.fromUri(uri)
					.replaceQueryParam("limit", limit)
					.replaceQueryParam("until", toInstant.apply(latest))
					.replaceQueryParam("since")
					.build()
					.encode()
					.toUri();
			return new Paging(previous, next);
		}

		Paging(URI previous, URI next) {
			this.previous = previous;
			this.next = next;
		}

		@JsonInclude(Include.NON_EMPTY)
		public URI getPrevious() {
			return previous;
		}

		@JsonInclude(Include.NON_EMPTY)
		public URI getNext() {
			return next;
		}
	}
}
