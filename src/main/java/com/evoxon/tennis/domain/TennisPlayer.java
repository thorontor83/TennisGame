package com.evoxon.tennis.domain;

public class TennisPlayer {

    private final String Name;

    private final int skillRating;

    public TennisPlayer(String name, int skillRating) {
        Name = name;
        this.skillRating = skillRating;
    }

    public String getName() {
        return Name;
    }

    public int getSkillRating() {
        return skillRating;
    }

    @Override
    public String toString() {
        return "TennisPlayer{" +
                "Name='" + Name + '\'' +
                ", skillRating=" + skillRating +
                '}';
    }
}
