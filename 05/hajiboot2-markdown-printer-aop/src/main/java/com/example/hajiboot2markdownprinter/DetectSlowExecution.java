package com.example.hajiboot2markdownprinter;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DetectSlowExecution {
	long threshold() default 500;
}
