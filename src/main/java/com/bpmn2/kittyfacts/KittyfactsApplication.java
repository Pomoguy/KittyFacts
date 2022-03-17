package com.bpmn2.kittyfacts;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class KittyfactsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KittyfactsApplication.class, args);
	}

}
