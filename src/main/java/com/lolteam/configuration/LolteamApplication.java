package com.lolteam.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.lolteam")
@ComponentScan(basePackages = {"com.lolteam", "com.lolteam.entities.treatment"})
public class LolteamApplication {

	public static void main(String[] args) {
		SpringApplication.run(LolteamApplication.class, args);
	}
}
