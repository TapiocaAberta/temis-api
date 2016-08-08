package com.sjcdigital.temis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableMongoRepositories("com.sjcdigital.temis.model.repositories")
public class TemisApplication {

	public static void main(final String[] args) {
		SpringApplication.run(TemisApplication.class, args);
	}

}
