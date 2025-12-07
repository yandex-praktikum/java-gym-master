package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeMap;

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
        //Проверить, что за вторник не вернулось занятий
        int countMonday = 0;
        NavigableSet<TimeOfDay> list = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).navigableKeySet();
        for (TimeOfDay elem : list){
            countMonday = countMonday + timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).get(elem).size();
        }
        Assertions.assertEquals(countMonday, 1);

        int countTuesday = 0;
        NavigableSet<TimeOfDay> list2 = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).navigableKeySet();
        for (TimeOfDay elem : list2){
            countTuesday = countTuesday + timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).get(elem).size();
        }
        Assertions.assertEquals(countTuesday, 0);
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
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), 1);
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size(), 0);
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size(), 2);


        int countMonday = 0;
        NavigableSet<TimeOfDay> list = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).navigableKeySet();
        for (TimeOfDay elem : list){
            countMonday = countMonday + timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).get(elem).size();
        }
        Assertions.assertEquals(countMonday, 1);

        int countTuesday = 0;
        NavigableSet<TimeOfDay> list2 = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).navigableKeySet();
        for (TimeOfDay elem : list2){
            countTuesday = countTuesday + timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).get(elem).size();
        }
        Assertions.assertEquals(countTuesday, 0);


        int countThursday = 0;
        int i = 0;
        NavigableSet<TimeOfDay> list3 = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).navigableKeySet();
        for (TimeOfDay elem : list3){
            countThursday = countThursday + timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).get(elem).size();
            if (i == 0) {
                Assertions.assertEquals(elem,  new TimeOfDay(13, 0));
            }
            else if (i == 1){
                Assertions.assertEquals(elem,  new TimeOfDay(20, 0));
            }
            i = i + 1;
        }
        Assertions.assertEquals(countThursday, 2);
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
        //Проверить, что за понедельник в 14:00 не вернулось занятий

        Assertions.assertEquals(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size(), 1);
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));
    }
}
