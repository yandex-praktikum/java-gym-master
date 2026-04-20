package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

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

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size());

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesday.isEmpty());
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

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours());
        Assertions.assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours());

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

        List<TrainingSession> mondaySessionAt13 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, mondaySessionAt13.size());

        List<TrainingSession> mondaySessionAt14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(mondaySessionAt14.isEmpty());
    }

    @Test
    void testEmptySchedule() {
        Timetable timetable = new Timetable();
        List<TrainingSession> fridaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);
        Assertions.assertTrue(fridaySessions.isEmpty());
    }

    @Test
    void testSessionsSortedByTime() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(16, 0)));

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);
        Assertions.assertEquals(2, sessions.size());
        Assertions.assertEquals(16, sessions.get(0).getTimeOfDay().getHours());
        Assertions.assertEquals(18, sessions.get(1).getTimeOfDay().getHours());
    }

    @Test
    void testDifferentGroupsSameDay() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(new Group("Дети", Age.CHILD, 60),
                coach, DayOfWeek.SATURDAY, new TimeOfDay(10,0)));
        timetable.addNewTrainingSession(new TrainingSession(new Group("Взрослые", Age.ADULT, 90),
                coach, DayOfWeek.SATURDAY, new TimeOfDay(12, 0)));

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.SATURDAY);
        Assertions.assertEquals(2, sessions.size());
    }

    @Test
    void testSingleCoachMultiplyTrainings() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        timetable.addNewTrainingSession(new TrainingSession(new Group("Дети", Age.CHILD, 60), coach,
                DayOfWeek.SUNDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(new Group("Дети", Age.CHILD, 60), coach,
                DayOfWeek.SUNDAY, new TimeOfDay(17, 0)));
        timetable.addNewTrainingSession(new TrainingSession(new Group("Дети", Age.CHILD, 60), coach,
                DayOfWeek.SATURDAY, new TimeOfDay(15, 0)));

        List<Timetable.CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(3, result.get(0).getCount());
    }

    @Test
    void testMultipleCoachesDifferentCounts() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");

        timetable.addNewTrainingSession(new TrainingSession(new Group("Дети", Age.CHILD, 60), coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(new Group("Дети", Age.CHILD, 60), coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0)));

        timetable.addNewTrainingSession(new TrainingSession(new Group("Взрослые", Age.ADULT, 90), coach2,
                DayOfWeek.SUNDAY, new TimeOfDay(12, 0)));

        List<Timetable.CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(coach1, result.get(0).getCoach());
        Assertions.assertEquals(2, result.get(0).getCount());

        Assertions.assertEquals(coach2, result.get(1).getCoach());
        Assertions.assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();
        List<Timetable.CounterOfTrainings> result = timetable.getCountByCoaches();
        Assertions.assertTrue(result.isEmpty());
    }

}
