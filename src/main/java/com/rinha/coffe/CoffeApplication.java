package com.rinha.coffe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CoffeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeApplication.class, args);
	}

}
