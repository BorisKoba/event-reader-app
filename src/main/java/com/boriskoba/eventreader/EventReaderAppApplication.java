package com.boriskoba.eventreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EventReaderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventReaderAppApplication.class, args);
	}

}
