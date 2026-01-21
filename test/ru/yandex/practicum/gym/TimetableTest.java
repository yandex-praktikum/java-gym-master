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

        //Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(singleTrainingSession, mondaySessions.get(0));

        //Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
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
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours());
        Assertions.assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours());

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
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
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, sessions.size());
        Assertions.assertEquals(singleTrainingSession, sessions.get(0));

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).isEmpty());
    }
    
    @Test
    void testGetTrainingSessionsForDayAndTimeDuplicates() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("A", "B", "C");
        Coach coach2 = new Coach("D", "E", "F");
        Group group = new Group("Test", Age.CHILD, 60);
        
        TrainingSession session1 = new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group, coach2, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        
        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        Assertions.assertEquals(2, sessions.size());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Ivanov", "Ivan", "Ivanovich");
        Coach coach2 = new Coach("Petrov", "Petr", "Petrovich");
        Coach coach3 = new Coach("Sidorov", "Sidr", "Sidorovich");
        
        Group group = new Group("Test", Age.ADULT, 60);
        
        // Coach 1: 3 sessions
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));
        
        // Coach 2: 1 session
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        
        // Coach 3: 2 sessions
        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.THURSDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SATURDAY, new TimeOfDay(10, 0)));
        
        List<CounterOfTrainings> stats = timetable.getCountByCoaches();
        
        Assertions.assertEquals(3, stats.size());
        
        Assertions.assertEquals(coach1, stats.get(0).getCoach());
        Assertions.assertEquals(3, stats.get(0).getCount());
        
        Assertions.assertEquals(coach3, stats.get(1).getCoach());
        Assertions.assertEquals(2, stats.get(1).getCount());
        
        Assertions.assertEquals(coach2, stats.get(2).getCoach());
        Assertions.assertEquals(1, stats.get(2).getCount());
    }

}
