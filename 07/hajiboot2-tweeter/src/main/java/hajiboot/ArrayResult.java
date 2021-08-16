package hajiboot;

import java.util.List;

public class ArrayResult<T> {
	private final List<T> data;

	public ArrayResult(List<T> data) {
		this.data = data;
	}

	public List<T> getData() {
		return data;
	}
}
