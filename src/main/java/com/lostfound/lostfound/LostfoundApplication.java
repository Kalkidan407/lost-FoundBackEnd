package com.lostfound.lostfound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LostfoundApplication {

	public static void main(String[] args) {
		SpringApplication.run(LostfoundApplication.class, args);
	}

}



