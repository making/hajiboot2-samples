package hajiboot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolationsException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ErrorControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, ?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		final Map<String, List<String>> details = e.getFieldErrors().stream()
				.collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, toList())));
		return ResponseEntity.badRequest().body(Map.of(
				"error", "Bad Request",
				"status", 400,
				"details", details));
	}

	@ExceptionHandler(ConstraintViolationsException.class)
	public ResponseEntity<Map<String, ?>> handleConstraintViolationsException(ConstraintViolationsException e) {
		final Map<String, List<String>> details = e.violations().stream()
				.collect(Collectors.groupingBy(ConstraintViolation::name, Collectors.mapping(ConstraintViolation::message, toList())));
		return ResponseEntity.badRequest().body(Map.of(
				"error", "Bad Request",
				"status", 400,
				"details", details));
	}
}
