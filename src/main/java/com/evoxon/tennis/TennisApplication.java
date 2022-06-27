package com.evoxon.tennis;

import com.evoxon.tennis.domain.TennisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TennisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisApplication.class, args);

		TennisService tennisService = new TennisService();
		tennisService.initializeGame();
	}

}
