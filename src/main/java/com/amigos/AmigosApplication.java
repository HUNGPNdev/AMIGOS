package com.amigos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmigosApplication {

	private static final Logger log = LoggerFactory.getLogger(AmigosApplication.class);

	public static void main(String[] args) {
		log.info("Starting Amigos Backend: Start (debug flag)");
		SpringApplication.run(AmigosApplication.class, args);
		log.info("Starting Amigos Backend: Finish");
	}
}
