package com.evoxon.tennis.domain;

public enum Tie {
    EMPTY(""),  ADVANTAGE_1("Advantage for Player 1"), DEUCE("Deuce"), ADVANTAGE_2("Advantage for Player 2");

    public final String value;

    private Tie(String value) {
        this.value = value;
    }

    public Tie toPlayer1() {
        return values()[ordinal() - 1];
    }
    public Tie toPlayer2() {
        return values()[ordinal() + 1];
    }
}
