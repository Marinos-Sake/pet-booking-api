package com.petbooking.bookingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class PetBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetBookingApplication.class, args);
	}

}
