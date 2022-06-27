package com.evoxon.tennis.domain;

public enum Points {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40);

    public final int value;

    private Points(int value) {
        this.value = value;
    }

    public Points next() {
        if (value == FORTY.value)
        {
            return values()[ordinal()];
        }
        else {
            return values()[ordinal() + 1];
        }
    }
}
