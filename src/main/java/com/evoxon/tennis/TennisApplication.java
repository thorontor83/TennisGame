package com.evoxon.tennis;

import com.evoxon.tennis.domain.TennisService;
import com.evoxon.tennis.util.KeyboardInput;
import com.evoxon.tennis.util.RandomNumber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TennisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisApplication.class, args);

		KeyboardInput keyboardInput= new KeyboardInput();
		RandomNumber randomNumber = new RandomNumber();
		TennisService tennisService = new TennisService(keyboardInput,randomNumber);
		tennisService.initializeGame();
	}

}
