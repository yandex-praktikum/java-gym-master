package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimetableTest {
    private static Coach mainCoach;
    private static Group childGroup;
    private static Group adultGroup;

    private Timetable timetable;

    @BeforeAll
    static void setupGlobalData() {
        mainCoach = new Coach("Васильев", "Николай", "Сергеевич");
        childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        adultGroup = new Group("Акробатика для взрослых", Age.ADULT, 90);
    }

    @BeforeEach
    void init() {
        timetable = new Timetable();
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        TrainingSession mondaySession = new TrainingSession(childGroup, mainCoach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(mondaySession);

        List<TrainingSession> mondayList = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> tuesdayList = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        //Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, mondayList.size());
        assertEquals(mondaySession, mondayList.get(0));
        //Проверить, что за вторник не вернулось занятий
        assertTrue(tuesdayList.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, mainCoach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));
        timetable.addNewTrainingSession(new TrainingSession(childGroup, mainCoach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)));

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        assertEquals(2, thursdaySessions.size());
        assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours());
        assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        TrainingSession sessionMonday = new TrainingSession(childGroup, mainCoach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(sessionMonday);

        List<TrainingSession> sessionFound = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        List<TrainingSession> sessionNotFound = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(1, sessionFound.size());
        assertEquals(sessionMonday, sessionFound.get(0));
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertTrue(sessionNotFound.isEmpty());
    }

    @Test
    void testAddMultipleSessionsSameTimeDifferentCoaches() {
        Coach coach2 = new Coach("Сидоров", "Антон", "Павлович");
        TimeOfDay time = new TimeOfDay(18, 0);

        TrainingSession s1 = new TrainingSession(childGroup, mainCoach, DayOfWeek.WEDNESDAY, time);
        TrainingSession s2 = new TrainingSession(adultGroup, coach2, DayOfWeek.WEDNESDAY, time);

        timetable.addNewTrainingSession(s1);
        timetable.addNewTrainingSession(s2);

        List<TrainingSession> wednesdaySessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.WEDNESDAY, time);

        assertEquals(2, wednesdaySessions.size());
        assertTrue(wednesdaySessions.contains(s1));
        assertTrue(wednesdaySessions.contains(s2));
    }

    @Test
    void testGetCountByCoachesSortingByTrainingCount() {
        Coach coach1 = new Coach("Иванов", "Петр", "Ильич");
        Coach coach2 = new Coach("Сидоров", "Антон", "Павлович");

        timetable.addNewTrainingSession(new TrainingSession(childGroup, mainCoach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(childGroup, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(childGroup, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coach1, DayOfWeek.TUESDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(childGroup, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coach2, DayOfWeek.MONDAY, new TimeOfDay(9, 0)));

        //проверка что все три тренера добавились
        assertEquals(3, timetable.getCountByCoaches().size());
        //проверка что список начинается с тренера с наибольшим количеством тренировок
        assertEquals(3, timetable.getCountByCoaches().getFirst().getTrainingCount());
        //проверка что список заканчивается с тренера с наименьшим количеством тренировок
        assertEquals(1, timetable.getCountByCoaches().getLast().getTrainingCount());
    }

    @Test
    void testEmptyTimetableReturnsEmptyList() {
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY).isEmpty());
        assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.FRIDAY, new TimeOfDay(15, 0)).isEmpty());
        assertTrue(timetable.getCountByCoaches().isEmpty());
    }
}
