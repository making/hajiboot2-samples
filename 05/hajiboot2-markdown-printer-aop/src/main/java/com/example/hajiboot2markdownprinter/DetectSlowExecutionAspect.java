package com.example.hajiboot2markdownprinter;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DetectSlowExecutionAspect {
	private final Logger log = LoggerFactory.getLogger(DetectSlowExecutionAspect.class);

	@Around("execution (@com.example.hajiboot2markdownprinter.DetectSlowExecution * *.*(..))")
	public Object detect(ProceedingJoinPoint pjp) throws Throwable {
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		DetectSlowExecution detectSlowExecution = AnnotationUtils.getAnnotation(method,
				DetectSlowExecution.class);
		long begin = System.currentTimeMillis();
		Object result = pjp.proceed();
		long elapsed = System.currentTimeMillis() - begin;
		if (elapsed >= detectSlowExecution.threshold()) {
			Object[] args = pjp.getArgs();
			log.warn("Detect slow execution elapsed={}ms, method={}, args={}", elapsed,
					method, Arrays.toString(args));
		}
		return result;
	}
}
