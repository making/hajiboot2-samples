package hajiboot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, ?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		final List<Map<String, String>> details = e.getFieldErrors().stream()
				.map(error -> Map.of(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(Map.of(
				"error", "Bad Request",
				"status", 400,
				"details", details));
	}
}
