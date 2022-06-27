package com.evoxon.tennis.domain;

import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Service
public class TennisService {

    Scanner scanner = new Scanner(System.in);

    public void initializeGame(){
        System.out.println("Would you like to play a new game of Tennis?: (Y/N)");
        String newGame = scanner.nextLine();
        if(newGame.compareTo("Y")==0 || newGame.compareTo("y")==0){
            TennisGame tennisGame = createNewGame();
            playTennisGame(tennisGame);
        }
        else{
            System.out.println("Goodbye");
        }
    }

    private void playTennisGame(TennisGame tennisGame) {
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

    private void ScorePoint(TennisGame tennisGame, int pointWinner) {
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

    private void scorePointDuringDeuce(TennisGame tennisGame, int pointWinner) {
        if(pointWinner==1){
            if (tennisGame.getTieScore()==Tie.ADVANTAGE_1){tennisGame.setFinished(true);}
            else{tennisGame.setTieScore(tennisGame.getTieScore().toPlayer1());}
        }
        if(pointWinner==2){
            if (tennisGame.getTieScore()==Tie.ADVANTAGE_2){tennisGame.setFinished(true);}
            else{tennisGame.setTieScore(tennisGame.getTieScore().toPlayer2());}
        }
    }

    private int playOnePoint(TennisPlayer player1, TennisPlayer player2) {
        Random random = new Random();
        int player1Performance = random.nextInt()*player1.getSkillRating();
        int player2Performance = random.nextInt()*player2.getSkillRating();
        if (player1Performance>player2Performance){return 1;}
        else {return 2;}
    }

    private TennisGame createNewGame() {
        System.out.println("Enter First Player Name:");
        String player1Name = scanner.nextLine();
        if(player1Name.isBlank()){player1Name = "Rafa Nadal";}
        System.out.println("Enter First Player Skill Level: (1-100)");
        String player1Skill = scanner.nextLine();
        if(player1Skill.isBlank()){player1Skill = "85";}
        System.out.println("Enter Second Player Name:");
        String player2Name = scanner.nextLine();
        if(player2Name.isBlank()){player2Name = "Roger Federer";}
        System.out.println("Enter Second Player Skill Level: (1-100)");
        String player2Skill = scanner.nextLine();
        if(player2Skill.isBlank()){player2Skill = "85";}
        TennisPlayer player1 = new TennisPlayer(player1Name,Integer.parseInt(player1Skill));
        TennisPlayer player2 = new TennisPlayer(player2Name,Integer.parseInt(player2Skill));
        TennisGame tennisGame = new TennisGame(player1, player2, Points.LOVE, Points.LOVE, Tie.EMPTY, false);
        System.out.println("");
        System.out.println("The Match is ready to begin: ");
        showScores(tennisGame);
        return tennisGame;
    }

    private void showScores(TennisGame tennisGame){
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
