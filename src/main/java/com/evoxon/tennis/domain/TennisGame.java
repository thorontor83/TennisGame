package com.evoxon.tennis.domain;

public class TennisGame {

    private final TennisPlayer player1;
    private final TennisPlayer player2;
    private Points score1;
    private Points score2;
    private Tie tieScore;
    private boolean Finished;

    public TennisGame(TennisPlayer player1, TennisPlayer player2, Points score1, Points score2, Tie tieScore, boolean finished) {
        this.player1 = player1;
        this.player2 = player2;
        this.score1 = score1;
        this.score2 = score2;
        this.tieScore = tieScore;
        Finished = finished;
    }

    public TennisPlayer getPlayer1() {
        return player1;
    }

    public TennisPlayer getPlayer2() {
        return player2;
    }

    public Points getScore1() {
        return score1;
    }

    public Points getScore2() {
        return score2;
    }

    public Tie getTieScore() {
        return tieScore;
    }

    public void setScore1(Points score1) {
        this.score1 = score1;
    }

    public boolean isFinished() {
        return Finished;
    }

    public void setFinished(boolean finished) {
        Finished = finished;
    }

    public void setScore2(Points score2) {
        this.score2 = score2;
    }

    public void setTieScore(Tie tieScore) {
        this.tieScore = tieScore;
    }
}
