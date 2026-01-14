package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(excludeName = {
	    "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration",
	    "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration"
	})
public class TaskFlowManagementSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskFlowManagementSystemBackendApplication.class, args);
	}

}
