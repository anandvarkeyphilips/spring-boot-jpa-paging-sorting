package io.pheonixlabs.spring.data.jpa.pagingsorting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootJpaPagingSortingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaPagingSortingApplication.class, args);
	}
}
