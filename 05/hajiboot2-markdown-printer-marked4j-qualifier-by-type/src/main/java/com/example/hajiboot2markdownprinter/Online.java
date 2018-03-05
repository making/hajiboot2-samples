package com.example.hajiboot2markdownprinter;

import java.lang.annotation.*;

import org.springframework.beans.factory.annotation.Qualifier;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD,
		ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier
public @interface Online {
}
