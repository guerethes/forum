package br.com.guerethes.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GerenciadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorApplication.class, args);
	}

}