package com.evoxon.tennis.domain;

import com.evoxon.tennis.util.KeyboardInput;
import com.evoxon.tennis.util.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TennisService {
    @Autowired
    private final KeyboardInput keyboardInput;
    @Autowired
    private final RandomNumber randomNumber;

    public TennisService(KeyboardInput keyboardInput, RandomNumber randomNumber) {
        this.keyboardInput = keyboardInput;
        this.randomNumber = randomNumber;
    }

    public void initializeGame(){
        System.out.println("Would you like to play a new game of Tennis?: (Y/N)");
        String newGame = keyboardInput.getNewInput();
        if(newGame.compareTo("Y")==0 || newGame.compareTo("y")==0){
            TennisGame tennisGame = createNewGame();
            playTennisGame(tennisGame);
        }
        else{
            System.out.println("Goodbye");
        }
    }

    public void playTennisGame(TennisGame tennisGame) {
        while (!tennisGame.isFinished()){
            int pointWinner = playOnePoint(tennisGame.getPlayer1(),tennisGame.getPlayer2());
            System.out.println("Point for Player " + pointWinner);
            ScorePoint(tennisGame, pointWinner);
            showScores(tennisGame);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The Match has finished.");
        initializeGame();
    }

    public void ScorePoint(TennisGame tennisGame, int pointWinner) {
        if (tennisGame.getTieScore() == Tie.EMPTY){
            if (pointWinner == 1){
                if(tennisGame.getScore1()==Points.FORTY)
                    tennisGame.setFinished(true);
                else
                    tennisGame.setScore1(tennisGame.getScore1().next());
            }
            if (pointWinner == 2){
                if(tennisGame.getScore2()==Points.FORTY)
                    tennisGame.setFinished(true);
                else
                    tennisGame.setScore2(tennisGame.getScore2().next());
            }
        }
        else {
            scorePointDuringDeuce(tennisGame, pointWinner);
        }
        if (tennisGame.getScore1() == Points.FORTY && tennisGame.getScore2() == Points.FORTY && tennisGame.getTieScore() == Tie.EMPTY) {
            tennisGame.setTieScore(Tie.DEUCE);
        }
    }

    public void scorePointDuringDeuce(TennisGame tennisGame, int pointWinner) {
        if(pointWinner==1){
            if (tennisGame.getTieScore()==Tie.ADVANTAGE_1){tennisGame.setFinished(true);}
            else{tennisGame.setTieScore(tennisGame.getTieScore().toPlayer1());}
        }
        if(pointWinner==2){
            if (tennisGame.getTieScore()==Tie.ADVANTAGE_2){tennisGame.setFinished(true);}
            else{tennisGame.setTieScore(tennisGame.getTieScore().toPlayer2());}
        }
    }

    public int playOnePoint(TennisPlayer player1, TennisPlayer player2) {
        Random random = new Random();
        long player1Performance = (long) randomNumber.getNewNumber()*player1.getSkillRating();
        long player2Performance = (long) randomNumber.getNewNumber()*player2.getSkillRating();
        if (player1Performance > player2Performance){return 1;}
        else {return 2;}
    }

    public  TennisGame createNewGame() {
        System.out.println("Enter First Player Name:");
        String player1Name = keyboardInput.getNewInput();
        if(player1Name.isBlank()){player1Name = "Rafa Nadal";}
        System.out.println("Enter First Player Skill Level: (1-100)");
        String player1Skill = keyboardInput.getNewInput();
        if(player1Skill.isBlank()){player1Skill = "85";}
        System.out.println("Enter Second Player Name:");
        String player2Name = keyboardInput.getNewInput();
        if(player2Name.isBlank()){player2Name = "Roger Federer";}
        System.out.println("Enter Second Player Skill Level: (1-100)");
        String player2Skill = keyboardInput.getNewInput();
        if(player2Skill.isBlank()){player2Skill = "85";}
        TennisPlayer player1 = new TennisPlayer(player1Name,Integer.parseInt(player1Skill));
        TennisPlayer player2 = new TennisPlayer(player2Name,Integer.parseInt(player2Skill));
        TennisGame tennisGame = new TennisGame(player1, player2, Points.LOVE, Points.LOVE, Tie.EMPTY, false);
        System.out.println("");
        System.out.println("The Match is ready to begin: ");
        showScores(tennisGame);
        return tennisGame;
    }

    public void showScores(TennisGame tennisGame){
        if (tennisGame.isFinished()){
            System.out.println("Match Point");
            System.out.println("The final score is: ");
        }
        System.out.println(tennisGame.getPlayer1().getName()+" "+tennisGame.getScore1().value+" "+
                tennisGame.getPlayer2().getName()+" "+tennisGame.getScore2().value);
        if (tennisGame.getTieScore() != Tie.EMPTY){
            System.out.println(tennisGame.getTieScore().value);
        }
        System.out.println("");
    }

}
