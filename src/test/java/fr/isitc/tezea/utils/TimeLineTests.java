package fr.isitc.tezea.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TimeLineTests {
    
    @Test
    void timeLineNotInConflictTest() {
        TimeLine tl1 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 0),
            LocalDateTime.of(2024, Month.MARCH, 6, 15, 0));

        TimeLine tl2 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 15, 1),
            LocalDateTime.of(2024, Month.MARCH, 6, 16, 0));


        assertFalse(TimeLine.areTimelineInConcurrence(tl1, tl2));
    }

    @Test
    void timeLineInConflictTest1() {
        TimeLine tl1 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 0),
            LocalDateTime.of(2024, Month.MARCH, 6, 15, 0));

        TimeLine tl2 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 10),
            LocalDateTime.of(2024, Month.MARCH, 6, 16, 0));


        assertTrue(TimeLine.areTimelineInConcurrence(tl1, tl2));
    }

    @Test
    void timeLineInConflictTest2() {
        TimeLine tl1 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 10),
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 30));

        TimeLine tl2 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 0),
            LocalDateTime.of(2024, Month.MARCH, 6, 15, 0));


        assertTrue(TimeLine.areTimelineInConcurrence(tl1, tl2));
    }

    @Test
    void sameTimeLineInConflictTest() {
        TimeLine tl1 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 0),
            LocalDateTime.of(2024, Month.MARCH, 6, 15, 0));

        TimeLine tl2 = new TimeLine(
            LocalDateTime.of(2024, Month.MARCH, 6, 14, 0),
            LocalDateTime.of(2024, Month.MARCH, 6, 15, 0));


        assertTrue(TimeLine.areTimelineInConcurrence(tl1, tl2));
    }
}
