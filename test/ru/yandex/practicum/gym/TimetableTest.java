package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySession.size());
        Assertions.assertEquals(singleTrainingSession, mondaySession.get(0));

        List<TrainingSession> tuesdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySession.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(mondayChildTrainingSession, mondaySessions.get(0));

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1));

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, sessions.size());
        Assertions.assertEquals(singleTrainingSession, sessions.get(0));

        List<TrainingSession> noSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(noSessions.isEmpty());
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(coach));
    }

    @Test
    void testGetCountByCoachesMultipleTrainings() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach, DayOfWeek.TUESDAY, new TimeOfDay(14, 0)));

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.get(coach));
    }

    @Test
    void testGetCountByCoachesSorted() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach1, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(
                new TrainingSession(group, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        List<Coach> coaches = new ArrayList<>(result.keySet());

        Assertions.assertEquals(coach1, coaches.get(0));
        Assertions.assertEquals(coach2, coaches.get(1));

        Assertions.assertEquals(2, result.get(coach1));
        Assertions.assertEquals(1, result.get(coach2));
    }
}
