package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
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

        //Проверить, что за понедельник вернулось одно занятие
        // Тут пришлось немного загуглить, как посчитать сумму элементов в мапе через map,
        // но зато обошлось без многоэтажных конструкций с циклами и тд
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
                .values()
                .stream()
                .mapToInt(List::size)
                .sum()
        );

        //Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY)
                .values()
                .stream()
                .mapToInt(List::size)
                .sum()
        );
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

        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
                .values()
                .stream()
                .mapToInt(List::size)
                .sum()
        );

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions
                .values()
                .stream()
                .mapToInt(List::size)
                .sum()
        );
        Set<TimeOfDay> thursdaySessionsOrder = thursdaySessions.keySet();
        Set<TimeOfDay> expectedThursdaySessionsOrder = Set.of(
                new TimeOfDay(13,0),
                new TimeOfDay(20, 0)
        );
        assertEquals(expectedThursdaySessionsOrder, thursdaySessionsOrder);

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySessions
                .values()
                .stream()
                .mapToInt(List::size)
                .sum()
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size());
    }

    @Test
    void testAddNewParallelSessionWithDifferentCoach() {
        Timetable timetable = new Timetable();

        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachTwo = new Coach("Иванов", "Сергей", "Петрович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coachOne,
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );

        Group groupHyrox = new Group("HYROX для взрослых", Age.ADULT, 90);
        TrainingSession thursdayHyroxSession = new TrainingSession(
                groupHyrox,
                coachTwo,
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(thursdayHyroxSession);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );

        assertTrue(sessions.contains(thursdayAdultTrainingSession));
        assertTrue(sessions.contains(thursdayHyroxSession));
    }

    @Test
    void testAddSessionsToSeveralDays() {
        Timetable timetable = new Timetable();

        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coachOne,
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );
        TrainingSession sundayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coachOne,
                DayOfWeek.SUNDAY,
                new TimeOfDay(15, 0)
        );

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(sundayAdultTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        TreeMap<TimeOfDay, List<TrainingSession>> sunday = timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY);

        assertEquals(1, thursday.values().stream().mapToInt(List::size).sum());
        assertEquals(1, sunday.values().stream().mapToInt(List::size).sum());
    }

    @Test
    void testCoachSessionsCounting() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coach,
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );
        TrainingSession sundayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coach,
                DayOfWeek.SUNDAY,
                new TimeOfDay(15, 0)
        );

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(sundayAdultTrainingSession);

        HashMap<Coach, Integer> counters = timetable.getCountByCoaches();

        assertEquals(2, counters.get(coach));
    }

    @Test
    void testTwoCoachesCountedCorrectly() {
        Timetable timetable = new Timetable();

        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachTwo = new Coach("Терентьев", "Терентий", "Терентьевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupSwim = new Group("Плавание", Age.ADULT, 55);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coachOne,
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );
        TrainingSession sundayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coachTwo,
                DayOfWeek.SUNDAY,
                new TimeOfDay(15, 0)
        );
        TrainingSession mondaySwim = new TrainingSession(
                groupSwim,
                coachTwo,
                DayOfWeek.MONDAY,
                new TimeOfDay(7,0)
        );
        TrainingSession wednesdaySwim = new TrainingSession(
                groupSwim,
                coachTwo,
                DayOfWeek.WEDNESDAY,
                new TimeOfDay(7, 0)
        );

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(sundayAdultTrainingSession);
        timetable.addNewTrainingSession(mondaySwim);
        timetable.addNewTrainingSession(wednesdaySwim);

        HashMap<Coach, Integer> counters = timetable.getCountByCoaches();

        assertEquals(1, counters.get(coachOne));
        assertEquals(3, counters.get(coachTwo));
    }

    @Test
    void testEmptyCouners_whenThereIsNoSessions() {
        Timetable timetable = new Timetable();

        HashMap<Coach, Integer> counters = timetable.getCountByCoaches();

        assertTrue(counters.isEmpty());
    }
}
