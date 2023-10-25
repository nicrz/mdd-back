package com.openclassrooms.mdd;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityRequirement(name = "bearerAuth")
@SpringBootApplication
public class MddApplication {

	public static void main(String[] args) {
		SpringApplication.run(MddApplication.class, args);
	}

}


