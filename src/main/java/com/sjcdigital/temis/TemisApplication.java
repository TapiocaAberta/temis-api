package com.sjcdigital.temis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableMongoRepositories("com.sjcdigital.temis.model.repositories")
public class TemisApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(TemisApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("POST", "PUT", "GET", "OPTIONS", "DELETE");
			}
		};
	}
	
}
