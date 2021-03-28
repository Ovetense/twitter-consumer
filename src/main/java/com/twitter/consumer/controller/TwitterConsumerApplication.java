package com.twitter.consumer.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories("com.twitter.consumer.data")
@EntityScan("com.twitter.consumer.entity")
@ComponentScan({"com.twitter"})
@EnableSwagger2
public class TwitterConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterConsumerApplication.class, args);
	}

}
