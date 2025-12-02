package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.DayOfWeek;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {
    private Timetable timetable;
    private Coach coach1;
    private Coach coach2;
    private Coach coach3;
    private Group group1;
    private Group group2;
    private Group group3;

    @BeforeEach
    public void setUp() {
        timetable = new Timetable();

        coach1 = new Coach(1, "Шмотков Владимир");
        coach2 = new Coach(2, "Тихомиров Андрей");
        coach3 = new Coach(3, "Миронов Юрий");

        group1 = new Group(1, "Детская гимнастика", 15, Age.CHILDREN);
        group2 = new Group(2, "Взрослые силовые тренировки", 20, Age.ADULTS);
        group3 = new Group(3, "Детская легкая атлетика", 10, Age.CHILDREN);
    }

    @Test
    public void testGetCountByCoaches_EmptyTimetable() {
        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCountByCoaches_SingleCoachMultipleSessions() {
        TrainingSession session1 = new TrainingSession(
                group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(
                group2, coach1, DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession session3 = new TrainingSession(
                group3, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(3, result.get(0).getTrainingCount());
    }

    @Test
    public void testGetCountByCoaches_MultipleCoachesDifferentCounts() {
        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach1, DayOfWeek.TUESDAY, new TimeOfDay(11, 0));
        TrainingSession session3 = new TrainingSession(group3, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));
        TrainingSession session4 = new TrainingSession(group1, coach2, DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession session5 = new TrainingSession(group2, coach2, DayOfWeek.TUESDAY, new TimeOfDay(15, 0));
        TrainingSession session6 = new TrainingSession(group1, coach3, DayOfWeek.FRIDAY, new TimeOfDay(9, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);
        timetable.addNewTrainingSession(session5);
        timetable.addNewTrainingSession(session6);

        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertEquals(3, result.size());

        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(3, result.get(0).getTrainingCount());

        assertEquals(coach2, result.get(1).getCoach());
        assertEquals(2, result.get(1).getTrainingCount());

        assertEquals(coach3, result.get(2).getCoach());
        assertEquals(1, result.get(2).getTrainingCount());
    }

    @Test
    public void testGetCountByCoaches_MultipleSessionsSameTime() {
        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session3 = new TrainingSession(group3, coach2, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        assertTrue(timetable.addNewTrainingSession(session1));
        assertTrue(timetable.addNewTrainingSession(session2));
        assertTrue(timetable.addNewTrainingSession(session3));

        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());

        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(2, result.get(0).getTrainingCount());

        assertEquals(coach2, result.get(1).getCoach());
        assertEquals(1, result.get(1).getTrainingCount());
    }

    @Test
    public void testGetCountByCoaches_SortingOrderWithEqualCounts() {
        Coach coachA = new Coach(4, "Алексеев Алексей");
        Coach coachB = new Coach(5, "Борисов Борис");

        TrainingSession session1 = new TrainingSession(group1, coachA, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coachB, DayOfWeek.TUESDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());

        assertEquals(coachA, result.get(0).getCoach());
        assertEquals(coachB, result.get(1).getCoach());
    }
}