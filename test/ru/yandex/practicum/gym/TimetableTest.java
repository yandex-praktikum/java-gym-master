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

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = getTimetable();

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        NavigableSet<TimeOfDay> timeOfDayNavigableSet = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).navigableKeySet();

        Assertions.assertEquals(13, timeOfDayNavigableSet.getFirst().getHours());
        Assertions.assertEquals(20, timeOfDayNavigableSet.getLast().getHours());

        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    private static Timetable getTimetable() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach1 = new Coach("Иванов", "Бронислав", "Иванович");
        Coach coach2 = new Coach("Демин", "Александр", "Дмитриевич");

        Group groupAdult1 = new Group("Растяжка для взрослых", Age.ADULT, 60);
        TrainingSession wednesdayTrainingSession = new TrainingSession(groupAdult1,coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12,0));

        Group groupAdult2 = new Group("Классический бодибилдинг", Age.ADULT, 120);
        TrainingSession sundayTrainingSession = new TrainingSession(groupAdult2,coach2,
                DayOfWeek.SUNDAY, new TimeOfDay(15,0));
        Group groupAdult3 = new Group("Позирование для бодибилдинга", Age.ADULT, 30);
        TrainingSession sundayTrainingSession2 = new TrainingSession(groupAdult3,coach2,
                DayOfWeek.SUNDAY, new TimeOfDay(17,0));

        timetable.addNewTrainingSession(wednesdayTrainingSession);
        timetable.addNewTrainingSession(sundayTrainingSession);
        timetable.addNewTrainingSession(sundayTrainingSession2);

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
        return timetable;
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TimeOfDay testTime1 = new TimeOfDay(13, 0);
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, testTime1).size());
        TimeOfDay testTime2 = new TimeOfDay(14, 0);
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,testTime2));

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testCountTrainingSessionsByCoaches(){
        Timetable timetable = getTimetable();
        List<CounterOfTrainingsByCoach> sessionsByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(4, sessionsByCoaches.get(0).getSessionsCount());
        Assertions.assertEquals(2, sessionsByCoaches.get(1).getSessionsCount());
        Assertions.assertEquals(1, sessionsByCoaches.get(2).getSessionsCount());
    }

    @Test
    void testOfSorting(){
        // Проверяем, что тренировки Демина будут на второй позиции по количеству тренировок
        Timetable timetable = getTimetable();
        List<CounterOfTrainingsByCoach> sessionsByCoaches = timetable.getCountByCoaches();

        Assertions.assertTrue(sessionsByCoaches.get(1).getSessionsCount() < sessionsByCoaches.get(0).getSessionsCount()
                && sessionsByCoaches.get(1).getSessionsCount() > sessionsByCoaches.get(2).getSessionsCount());
    }

    @Test
    void updateCountOfSessionsTest(){
        // Проверяем, что после добавления и обновления тренировок у тренера он поднимется по списку с количеством тренировок на первое место
        Timetable timetable = getTimetable();
        List<CounterOfTrainingsByCoach> sessionsByCoaches = timetable.getCountByCoaches();

        Coach coach = new Coach("Демин", "Александр", "Дмитриевич");

        Group groupAdult4 = new Group("Тренировка ног", Age.ADULT, 120);
        TrainingSession wednesdayTrainingSession1 = new TrainingSession(groupAdult4,coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20,0));

        Group groupAdult5 = new Group("Тренировка рук", Age.ADULT, 120);
        TrainingSession fridayTrainingSession = new TrainingSession(groupAdult5,coach,
                DayOfWeek.FRIDAY, new TimeOfDay(9,0));

        Group groupAdult6 = new Group("Тренировка спины", Age.ADULT, 120);
        TrainingSession sundayTrainingSession1 = new TrainingSession(groupAdult6,coach,
                DayOfWeek.SUNDAY, new TimeOfDay(10,0));


        //Проверяем, что возвращается то количество тренировок, которое было изначально
        Assertions.assertEquals(2, sessionsByCoaches.get(1).getSessionsCount());

        // Добавляем тренировки и проверяем их количество и сортировку по тренерам
        timetable.addNewTrainingSession(wednesdayTrainingSession1);
        timetable.addNewTrainingSession(fridayTrainingSession);
        timetable.addNewTrainingSession(sundayTrainingSession1);

        // Пересчитываем количество тренировок у тренеров
        List<CounterOfTrainingsByCoach> sessionsByCoachesUpdate = timetable.getCountByCoaches();

        Assertions.assertEquals("Демин", sessionsByCoachesUpdate.getFirst().getCoach());
        Assertions.assertEquals(5, sessionsByCoachesUpdate.getFirst().getSessionsCount());
    }
}