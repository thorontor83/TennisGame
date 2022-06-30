package com.evoxon.tennis.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumber {

    Random random = new Random();

    public int getNewNumber(){
        return random.nextInt(100);
    }
}
