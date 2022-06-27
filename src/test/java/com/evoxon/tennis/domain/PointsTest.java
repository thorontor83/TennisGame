package com.evoxon.tennis.domain;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PointsTest {

    @Test
    void shouldNext() {
        //given
        Points point1 = Points.LOVE;
        Points point2 = Points.FIFTEEN;
        Points point3 = Points.THIRTY;
        Points point4 = Points.FORTY;
        //when
        point1 = point1.next();
        point2 = point2.next();
        point3 = point3.next();
        point4 = point4.next();
        //then
        assertThat(point1).isEqualTo(Points.FIFTEEN);
        assertThat(point2).isEqualTo(Points.THIRTY);
        assertThat(point3).isEqualTo(Points.FORTY);
        assertThat(point4).isEqualTo(Points.FORTY);
    }
}