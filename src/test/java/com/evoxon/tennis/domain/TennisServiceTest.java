package com.evoxon.tennis.domain;

import com.evoxon.tennis.util.KeyboardInput;
import com.evoxon.tennis.util.RandomNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TennisServiceTest {

    @InjectMocks
    private TennisService tennisService;
    @Mock
    private KeyboardInput keyboardInput;
    @Mock
    private RandomNumber randomNumber;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void shouldInitializeGame() {
        //given
        final TennisPlayer player1 = new TennisPlayer("player1",50);
        final TennisPlayer player2 = new TennisPlayer("player2",50);
        final TennisGame tennisGame = new TennisGame(player1,player2,Points.LOVE,Points.LOVE,Tie.EMPTY,false);
        when(keyboardInput.getNewInput()).thenReturn("Y");
        TennisService tennisService1 = Mockito.spy(tennisService);
        Mockito.doReturn(tennisGame).when(tennisService1).createNewGame();
        Mockito.doNothing().when(tennisService1).playTennisGame(tennisGame);
        //when
        tennisService1.initializeGame();
        //then
        verify(tennisService1,times(1)).playTennisGame(tennisGame);
        verify(tennisService1,times(1)).createNewGame();
    }

    @Test
    void shouldNotInitializeGame() {
        //given
        when(keyboardInput.getNewInput()).thenReturn("N");
        //when
        tennisService.initializeGame();
        //then
        assertThat(outputStreamCaptor.toString()).contains("Goodbye");
    }

    @Test
    void shouldPlayOnePoint() {
        //given
        final TennisPlayer player1 = new TennisPlayer("player1",50);
        final TennisPlayer player2 = new TennisPlayer("player2",50);
        when(randomNumber.getNewNumber())
                .thenReturn(10)
                .thenReturn(90);
        //when
        int winnerPlayer = tennisService.playOnePoint(player1,player2);
        //then
        assertThat(winnerPlayer).isEqualTo(2);
    }

    @Test
    void shouldPlayTennisGame() {
        //given
        final TennisPlayer player1 = new TennisPlayer("player1",50);
        final TennisPlayer player2 = new TennisPlayer("player2",50);
        final TennisGame tennisGame = new TennisGame(player1,player2,Points.FORTY,Points.LOVE,Tie.EMPTY,false);
        TennisService tennisService1 = Mockito.spy(tennisService);
        Mockito.doReturn(1).when(tennisService1).playOnePoint(tennisGame.getPlayer1(),tennisGame.getPlayer2());
        Mockito.doNothing().when(tennisService1).initializeGame();
        //when
        tennisService1.playTennisGame(tennisGame);
        //then
        assertThat(tennisGame.isFinished()).isEqualTo(true);
        assertThat(outputStreamCaptor.toString()).contains("The Match has finished.");
    }

    @Test
    void shouldScorePoint() {
        //given
        final TennisPlayer player1 = new TennisPlayer("player1",50);
        final TennisPlayer player2 = new TennisPlayer("player2",50);
        final TennisGame tennisGame1 = new TennisGame(player1,player2,Points.LOVE,Points.LOVE,Tie.EMPTY,false);
        final TennisGame tennisGame2 = new TennisGame(player1,player2,Points.FORTY,Points.LOVE,Tie.EMPTY,false);
        final TennisGame tennisGame3 = new TennisGame(player1,player2,Points.LOVE,Points.LOVE,Tie.EMPTY,false);
        final TennisGame tennisGame4 = new TennisGame(player1,player2,Points.THIRTY,Points.FORTY,Tie.EMPTY,false);
        final TennisGame tennisGame5 = new TennisGame(player1,player2,Points.THIRTY,Points.FORTY,Tie.EMPTY,false);
        final TennisGame tennisGame6 = new TennisGame(player1,player2,Points.FORTY,Points.FORTY,Tie.DEUCE,false);
        //when
        tennisService.ScorePoint(tennisGame1,1);
        tennisService.ScorePoint(tennisGame2,1);
        tennisService.ScorePoint(tennisGame3,2);
        tennisService.ScorePoint(tennisGame4,1);
        tennisService.ScorePoint(tennisGame5,2);
        tennisService.ScorePoint(tennisGame6,1);

        //then
        assertThat(tennisGame1.getScore1()).isEqualTo(Points.FIFTEEN);
        assertThat(tennisGame2.isFinished()).isEqualTo(true);
        assertThat(tennisGame3.getScore2()).isEqualTo(Points.FIFTEEN);
        assertThat(tennisGame4.getTieScore()).isEqualTo(Tie.DEUCE);
        assertThat(tennisGame5.isFinished()).isEqualTo(true);
        assertThat(tennisGame6.getTieScore()).isEqualTo(Tie.ADVANTAGE_1);
    }

    @Test
    void shouldScorePointDuringDeuce() {
        //given
        final TennisPlayer player1 = new TennisPlayer("player1",50);
        final TennisPlayer player2 = new TennisPlayer("player2",50);
        final TennisGame tennisGame1 = new TennisGame(player1,player2,Points.FORTY,Points.FORTY,Tie.DEUCE,false);
        final TennisGame tennisGame2 = new TennisGame(player1,player2,Points.FORTY,Points.FORTY,Tie.DEUCE,false);
        final TennisGame tennisGame3 = new TennisGame(player1,player2,Points.FORTY,Points.FORTY,Tie.ADVANTAGE_1,false);
        final TennisGame tennisGame4 = new TennisGame(player1,player2,Points.FORTY,Points.FORTY,Tie.ADVANTAGE_2,false);
        //when
        tennisService.scorePointDuringDeuce(tennisGame1,1);
        tennisService.scorePointDuringDeuce(tennisGame2,2);
        tennisService.scorePointDuringDeuce(tennisGame3,1);
        tennisService.scorePointDuringDeuce(tennisGame4,2);
        //then
        assertThat(tennisGame1.getTieScore()).isEqualTo(Tie.ADVANTAGE_1);
        assertThat(tennisGame2.getTieScore()).isEqualTo(Tie.ADVANTAGE_2);
        assertThat(tennisGame3.isFinished()).isEqualTo(true);
        assertThat(tennisGame4.isFinished()).isEqualTo(true);

    }

    @Test
    void shouldCreateNewGame() {
        //given
        final TennisPlayer player1 = new TennisPlayer("Carlos Moya",65);
        final TennisPlayer player2 = new TennisPlayer("Andre Agassi",80);
        final TennisGame tennisGameExpected = new TennisGame(player1,player2,Points.LOVE,Points.LOVE,Tie.EMPTY,false);
        when(keyboardInput.getNewInput())
                .thenReturn("Carlos Moya")
                .thenReturn("65")
                .thenReturn("Andre Agassi")
                .thenReturn("80");
        //when
        TennisGame tennisGame = tennisService.createNewGame();
        //then
        assertThat(tennisGame.getPlayer1().getName()).isEqualTo(tennisGameExpected.getPlayer1().getName());
        assertThat(tennisGame.getPlayer2().getName()).isEqualTo(tennisGameExpected.getPlayer2().getName());
        assertThat(tennisGame.getPlayer1().getSkillRating()).isEqualTo(tennisGameExpected.getPlayer1().getSkillRating());
        assertThat(tennisGame.getPlayer2().getSkillRating()).isEqualTo(tennisGameExpected.getPlayer2().getSkillRating());
    }

    @Test
    void shouldShowScores() {
        //given
        final TennisPlayer player1 = new TennisPlayer("player1",50);
        final TennisPlayer player2 = new TennisPlayer("player2",50);
        final TennisGame tennisGame1 = new TennisGame(player1,player2,Points.FORTY,Points.FORTY,Tie.ADVANTAGE_1,true);
        //when
        tennisService.showScores(tennisGame1);
        //then
        assertThat(outputStreamCaptor.toString()).contains("Match Point");
        assertThat(outputStreamCaptor.toString()).contains("player1 40 player2 40");
        assertThat(outputStreamCaptor.toString()).contains("Advantage for Player 1");
    }
}