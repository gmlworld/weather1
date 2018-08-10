package com.gongml.weather1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@EnableScheduling
public class Weather1Application {

	public static void main(String[] args) {
		SpringApplication.run(Weather1Application.class, args);
	}
}
