package com.example.hajiboot2markdownprinter;

import org.junit.Test;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.boot.SpringApplication;

public class Hajiboot2MarkdownPrinterApplicationTests {

	@Test(expected = UnsatisfiedDependencyException.class)
	public void contextLoads() {
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class);
	}

}
