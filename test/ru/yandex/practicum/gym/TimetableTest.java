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

        TreeMap<TimeOfDay,ArrayList<TrainingSession>> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, mondaySessions.size());
        //проверка, что вернулась тренировка в 13:00
        Assertions.assertTrue(mondaySessions.containsKey(new TimeOfDay(13, 0)));
        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
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
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> trainingSessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingSessions.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        List<TimeOfDay> thursdayTimes = new ArrayList<>(thursdaySessions.keySet());
        Assertions.assertEquals(new TimeOfDay(13, 0), thursdayTimes.get(0));
        Assertions.assertEquals(new TimeOfDay(20, 0), thursdayTimes.get(1));

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
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

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingSessions.size());

        //проверка, что вернулась тренировка за 13:00
        Assertions.assertEquals(singleTrainingSession, trainingSessions.getFirst());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        Assertions.assertTrue(trainingSessions.isEmpty());

    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иваныч");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);



        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach1, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coach1, DayOfWeek.TUESDAY,
                new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach1, DayOfWeek.WEDNESDAY,
                new TimeOfDay(12, 0)));


        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coach2, DayOfWeek.MONDAY,
                new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach2, DayOfWeek.FRIDAY,
                new TimeOfDay(10, 0)));

        LinkedHashMap<Coach,Integer> coachTrainingsCountsList = timetable.getCountByCoaches();
        Assertions.assertEquals(3, coachTrainingsCountsList.get(coach1)); //у coach1 3 тренировки
        Assertions.assertEquals(2, coachTrainingsCountsList.get(coach2)); //у coach2 2 тренировки

        // Проверяем порядок по убыванию количества занятий
        List<Coach> coaches = new ArrayList<>(coachTrainingsCountsList.keySet());
        Assertions.assertEquals(coach1, coaches.get(0));
        Assertions.assertEquals(coach2, coaches.get(1));

    }

}
