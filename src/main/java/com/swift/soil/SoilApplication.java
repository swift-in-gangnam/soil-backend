package com.swift.soil;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SoilApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location=" + "classpath:application.yml," + "/home/ec2-user/config/aws.yml," + "/home/ec2-user/config/application-prod.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(SoilApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}
}
