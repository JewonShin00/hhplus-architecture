package com.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.architecture.application", "com.architecture.infrastructure"})
public class MyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}
}