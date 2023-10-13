package ru.batorov.testTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}

}
