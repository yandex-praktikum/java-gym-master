package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
        Map<TimeOfDay, List<TrainingSession>> mondaySession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySession.size(), "В понедельник должно быть одно занятие");

        //Проверить, что за вторник не вернулось занятий
        Map<TimeOfDay, List<TrainingSession>> tuesdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySession.size(), "Во вторник занятий не должно быть");
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
        Map<TimeOfDay, List<TrainingSession>> mondaySession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySession.size(), "В понедельник должно быть одно занятие");

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Map<TimeOfDay, List<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size(), "Ожидалось 2 занятия в четверг");

        // Получаем ключи (время) — они должны быть отсортированы благодаря TreeMap
        List<TimeOfDay> times = new ArrayList<>(thursdaySessions.keySet());
        // Проверяем порядок времени
        assertEquals(new TimeOfDay(13, 0), times.get(0), "Первое занятие должно быть в 13:00");
        assertEquals(new TimeOfDay(20, 0), times.get(1), "Второе занятие должно быть в 20:00");

        // Проверить, что за вторник не вернулось занятий
        Map<TimeOfDay, List<TrainingSession>> tuesdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySession.size(), "Во вторник занятий не должно быть");
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
        List<TrainingSession> monday1300Sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        assertEquals(1, monday1300Sessions.size(), "Должно быть одно занятие в понедельник в 13:00");

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> monday1400Sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        assertEquals(0, monday1400Sessions.size(), "Ожидалось, что в 14:00 занятий нет");
    }

    @Test
    void shouldReturnEmptyListWhenNoSessions() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertNotNull(result, "Список не должен быть null");
        assertTrue(result.isEmpty(), "Список должен быть пустым, если нет тренировок");
    }

    @Test
    void mustReturnOneCoachFromDifferentTrainingSessions() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Сергеев", "Сергей", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.WEDNESDAY,
                new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.SATURDAY,
                new TimeOfDay(10, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(1, result.size(), "Должен быть один тренер в списке");

        CounterOfTrainings counter = result.get(0);
        assertEquals(coach, counter.getCoach(), "Тренер должен совпадать");
        assertEquals(3, counter.getCount(), "Количество тренировок должно быть 3");
    }

    @Test
    void shouldSortTheTrainersInDescendingOrderOfTheNumberOfWorkouts() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич"); // 3 тренировки
        Coach coach2 = new Coach("Петрова", "Анна", "Владимировна"); // 2 тренировки
        Coach coach3 = new Coach("Сидоров", "Иван", "Михайлович");    // 1 тренировка

        Group group = new Group("Акробатика", Age.CHILD, 90);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.TUESDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.THURSDAY,
                new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.WEDNESDAY,
                new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.FRIDAY,
                new TimeOfDay(18, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SUNDAY,
                new TimeOfDay(12, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(3, result.size(), "Должно быть 3 тренера");

        assertEquals(coach1, result.get(0).getCoach(), "Первым должен быть Васильев (3 тренировки)");
        assertEquals(coach2, result.get(1).getCoach(), "Второй — Петрова (2 тренировки)");
        assertEquals(coach3, result.get(2).getCoach(), "Третий — Сидоров (1 тренировка)");
    }
}
