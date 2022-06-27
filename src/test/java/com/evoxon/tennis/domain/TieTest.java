package com.evoxon.tennis.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TieTest {

    @Test
    void shouldToPlayer1() {
        //given
        Tie tie1 = Tie.EMPTY;
        Tie tie2 = Tie.ADVANTAGE_1;
        Tie tie3 = Tie.DEUCE;
        Tie tie4 = Tie.ADVANTAGE_2;
        //when
        tie1 = tie1.toPlayer1();
        tie2 = tie2.toPlayer1();
        tie3 = tie3.toPlayer1();
        tie4 = tie4.toPlayer1();
        //then
        assertThat(tie1).isEqualTo(Tie.EMPTY);
        assertThat(tie2).isEqualTo(Tie.ADVANTAGE_1);
        assertThat(tie3).isEqualTo(Tie.ADVANTAGE_1);
        assertThat(tie4).isEqualTo(Tie.DEUCE);
    }

    @Test
    void shouldToPlayer2() {
        //given
        Tie tie1 = Tie.EMPTY;
        Tie tie2 = Tie.ADVANTAGE_1;
        Tie tie3 = Tie.DEUCE;
        Tie tie4 = Tie.ADVANTAGE_2;
        //when
        tie1 = tie1.toPlayer2();
        tie2 = tie2.toPlayer2();
        tie3 = tie3.toPlayer2();
        tie4 = tie4.toPlayer2();
        //then
        assertThat(tie1).isEqualTo(Tie.EMPTY);
        assertThat(tie2).isEqualTo(Tie.DEUCE);
        assertThat(tie3).isEqualTo(Tie.ADVANTAGE_2);
        assertThat(tie4).isEqualTo(Tie.ADVANTAGE_2);
    }
}