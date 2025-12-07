package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), 1);
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size(), 0);
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

        //TreeMap<TimeOfDay,TrainingSession> curTreeMap = timetable.get(DayOfWeek.THURSDAY);
        int i = 0;
        for (TrainingSession trainingSession : timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).values()){
            if (i == 0) {
                Assertions.assertEquals(trainingSession.getTimeOfDay(),  new TimeOfDay(13, 0));
            }
            else if (i == 1){
                Assertions.assertEquals(trainingSession.getTimeOfDay(),  new TimeOfDay(20, 0));
            }
            i = i + 1;
        }
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
        Assertions.assertNotNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));
    }

}
