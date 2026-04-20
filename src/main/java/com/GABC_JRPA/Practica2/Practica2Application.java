package com.GABC_JRPA.Practica2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication

@EnableJdbcRepositories(basePackages = "com.GABC_JRPA.Practica2.repository")
public class Practica2Application {

	public static void main(String[] args) {
		SpringApplication.run(Practica2Application.class, args);
	}

}