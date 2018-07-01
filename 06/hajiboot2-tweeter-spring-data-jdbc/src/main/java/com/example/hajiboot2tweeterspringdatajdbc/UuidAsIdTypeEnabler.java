package com.example.hajiboot2tweeterspringdatajdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

// https://jira.spring.io/browse/DATAJDBC-229
@Component
public class UuidAsIdTypeEnabler implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof RelationalMappingContext) {
			HashSet<Class<?>> CUSTOM_SIMPLE_TYPES = new HashSet<>(asList( //
					BigDecimal.class, //
					BigInteger.class, //
					Temporal.class, //
					UUID.class));
			((RelationalMappingContext) bean)
					.setSimpleTypeHolder(new SimpleTypeHolder(CUSTOM_SIMPLE_TYPES, true));
		}
		return bean;
	}
}
