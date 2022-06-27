package com.evoxon.tennis.domain;

public enum Tie {
    EMPTY(""), ADVANTAGE_1("Advantage for Player 1"), DEUCE("Deuce"), ADVANTAGE_2("Advantage for Player 2");

    public final String value;

    private Tie(String value) {
        this.value = value;
    }

    public Tie toPlayer1() {
        if (value == EMPTY.value || value == ADVANTAGE_1.value)
            return values()[ordinal()];
        else
            return values()[ordinal() - 1];
    }

    public Tie toPlayer2() {
        if (value == EMPTY.value || value == ADVANTAGE_2.value)
            return values()[ordinal()];
        else
            return values()[ordinal() + 1];
    }
}
