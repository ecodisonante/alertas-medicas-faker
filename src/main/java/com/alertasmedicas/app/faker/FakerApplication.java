package com.alertasmedicas.app.faker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Habilita las tareas programadas
public class FakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakerApplication.class, args);
	}

}
