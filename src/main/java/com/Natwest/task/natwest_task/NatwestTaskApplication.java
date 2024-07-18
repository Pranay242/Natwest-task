package com.Natwest.task.natwest_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.Natwest.*")
@EnableScheduling
public class NatwestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(NatwestTaskApplication.class, args);
	}

}
