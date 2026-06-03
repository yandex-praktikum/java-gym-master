package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        //Вопрос создание этих переменных лучше кидать вверх кода или лучше оставить так?(mondayTrainings, tuesdayTrainings)
        TreeMap<TimeOfDay, List<TrainingSession>> mondayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondayTrainings.size());
        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, tuesdayTrainings.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        TimeOfDay timeOfFirstTraining = new TimeOfDay(13, 0);
        TimeOfDay timeOfSecondTraining = new TimeOfDay(20, 0);

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondayTrainings.size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TimeOfDay> keys = new ArrayList<>(thursdayTrainings.keySet());
        Assertions.assertEquals(timeOfFirstTraining, keys.get(0));
        Assertions.assertEquals(timeOfSecondTraining, keys.get(1));
        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, tuesdayTrainings.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TimeOfDay timeOfFirstTraining = new TimeOfDay(13, 0);
        TimeOfDay timeOfSecondTraining = new TimeOfDay(14, 0);

        timetable.addNewTrainingSession(singleTrainingSession);
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> mondaysTrainings = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfFirstTraining);
        Assertions.assertEquals(1, mondaysTrainings.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> tuesdayTrainings = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, timeOfSecondTraining);
        Assertions.assertEquals(0, tuesdayTrainings.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessionsInDifferentOrder() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession mondaySecondChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        TrainingSession mondayThirdChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(21, 0));

        TimeOfDay timeOfFirstTraining = new TimeOfDay(13, 0);
        TimeOfDay timeOfSecondTraining = new TimeOfDay(20, 0);
        TimeOfDay timeOfThirdTraining = new TimeOfDay(21, 0);

        timetable.addNewTrainingSession(mondaySecondChildTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(mondayThirdChildTrainingSession);

        // Проверяем, что сортировка тренировок от большей к меньшей
        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TimeOfDay> keys = new ArrayList<>(thursdayTrainings.keySet());
        Assertions.assertEquals(timeOfFirstTraining, keys.get(0));
        Assertions.assertEquals(timeOfSecondTraining, keys.get(1));
        Assertions.assertEquals(timeOfThirdTraining, keys.get(2));
    }

    @Test
    void testGetTrainingSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession mondaySecondChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        TrainingSession mondayThirdChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(21, 0));

        TimeOfDay timeOfFirstTraining = new TimeOfDay(13, 0);
        TimeOfDay timeOfSecondTraining = new TimeOfDay(20, 0);
        TimeOfDay timeOfThirdTraining = new TimeOfDay(21, 0);

        timetable.addNewTrainingSession(mondaySecondChildTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(mondayThirdChildTrainingSession);

        // Проверяем, что сортировка тренировок от большей к меньшей
        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TimeOfDay> keys = new ArrayList<>(thursdayTrainings.keySet());
        Assertions.assertEquals(timeOfFirstTraining, keys.get(0));
        Assertions.assertEquals(timeOfSecondTraining, keys.get(1));
        Assertions.assertEquals(timeOfThirdTraining, keys.get(2));
    }

    @Test
    void testGetTrainingSessionsForDay_ReturnsEmptyMapForNonExistentDay() {
        Timetable timetable = new Timetable();

        TreeMap<TimeOfDay, List<TrainingSession>> wednesdayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);

        Assertions.assertNotNull(wednesdayTrainings);
        Assertions.assertEquals(0, wednesdayTrainings.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime_MultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Иван", "Алексеевич");
        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TimeOfDay time = new TimeOfDay(15, 0);

        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.WEDNESDAY, time);
        TrainingSession session2 = new TrainingSession(group2, coach2, DayOfWeek.WEDNESDAY, time);

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.WEDNESDAY, time);

        Assertions.assertEquals(2, trainingSessions.size());
        Assertions.assertTrue(trainingSessions.contains(session1));
        Assertions.assertTrue(trainingSessions.contains(session2));
    }

    ///
    @Test
    void testCountOfTrainings() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(timetable.countOfTrainings().size(), 1);
    }

    @Test
    void testCountOfTrainingsIfCoachesDifferent() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Coach coach2 = new Coach("Васильев2", "Николай2", "Сергеевич2");
        TrainingSession secondTrainingSection = new TrainingSession(group, coach2, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSection);
        timetable.addNewTrainingSession(secondTrainingSection);

        Assertions.assertEquals(2, timetable.countOfTrainings().get(0).getCountOfTrainings());
        Assertions.assertEquals(1, timetable.countOfTrainings().get(1).getCountOfTrainings());
    }

    @Test
    void testCountOfTrainingsIfCoachesSimilar() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Coach coach2 = new Coach("Васильев2", "Николай2", "Сергеевич2");
        TrainingSession secondTrainingSection = new TrainingSession(group, coach2, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Coach coach3 = new Coach("Васильев3", "Николай3", "Сергеевич3");
        TrainingSession thirdTrainingSection = new TrainingSession(group, coach3, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSection);
        timetable.addNewTrainingSession(secondTrainingSection);
        timetable.addNewTrainingSession(thirdTrainingSection);
        timetable.addNewTrainingSession(thirdTrainingSection);
        timetable.addNewTrainingSession(thirdTrainingSection);

        Assertions.assertEquals(3, timetable.countOfTrainings().get(0).getCountOfTrainings());
        Assertions.assertEquals(2, timetable.countOfTrainings().get(1).getCountOfTrainings());
        Assertions.assertEquals(1, timetable.countOfTrainings().get(2).getCountOfTrainings());
    }
}
