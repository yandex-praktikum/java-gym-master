package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoachTrainingCountTest {

    @Test
    public void testCompareTo_DifferentCounts() {
        Coach coach1 = new Coach(1, "Шмотков");
        Coach coach2 = new Coach(2, "Тихомиров");

        CoachTrainingCount count1 = new CoachTrainingCount(coach1, 5);
        CoachTrainingCount count2 = new CoachTrainingCount(coach2, 3);

        assertTrue(count1.compareTo(count2) < 0);
        assertTrue(count2.compareTo(count1) > 0);
    }

    @Test
    public void testCompareTo_EqualCountsDifferentNames() {
        Coach coach1 = new Coach(1, "Беляев");
        Coach coach2 = new Coach(2, "Семенов");

        CoachTrainingCount count1 = new CoachTrainingCount(coach1, 3);
        CoachTrainingCount count2 = new CoachTrainingCount(coach2, 3);

        assertTrue(count1.compareTo(count2) < 0);
        assertTrue(count2.compareTo(count1) > 0);
    }

    @Test
    public void testConstructorValidation_NegativeCount() {
        Coach coach = new Coach(1, "Шмотков");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new CoachTrainingCount(coach, -1)
        );
        assertEquals("Training count cannot be negative.", exception.getMessage());
    }

    @Test
    public void testGetters() {
        Coach coach = new Coach(1, "Шмотков");
        CoachTrainingCount count = new CoachTrainingCount(coach, 5);

        assertEquals(coach, count.getCoach());
        assertEquals(5, count.getTrainingCount());
    }
}