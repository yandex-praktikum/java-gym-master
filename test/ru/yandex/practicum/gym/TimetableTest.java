package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import java.util.List;

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

        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertNotNull(monday);
        assertEquals(1, monday.size(), "Ожидаем одно занятие в понедельник");
        assertEquals(new TimeOfDay(13, 0), monday.get(0).getTimeOfDay());
        assertEquals(group, monday.get(0).getGroup());
        assertEquals(coach, monday.get(0).getCoach());

        // Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNotNull(tuesday);
        assertTrue(tuesday.isEmpty(), "Ожидается пустой список для вторника");
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
        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, monday.size(), "Ожидается одно занятие в понедельник");
        assertEquals(new TimeOfDay(13, 0), monday.get(0).getTimeOfDay());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursday.size(), "Ожидается два занятия в четверг");
        assertEquals(new TimeOfDay(13, 0), thursday.get(0).getTimeOfDay(), "Первое занятие должно быть в 13:00");
        assertEquals(new TimeOfDay(20, 0), thursday.get(1).getTimeOfDay(), "Второе занятие должно быть в 20:00");

        // Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesday.isEmpty(), "Ожидается пустой список для вторника");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> at13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertNotNull(at13);
        assertEquals(1, at13.size(), "Ожидается одно занятие в понедельник в 13:00");
        assertEquals(coach, at13.get(0).getCoach());

        // Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> at14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertNotNull(at14);
        assertTrue(at14.isEmpty(), "Ожидается пустой список для понедельника в 14:00");
    }

    // --- ТЕСТЫ ДЛЯ getCountByCoaches() ---

    @Test
    void testGetCountByCoachesBasic() {
        Timetable timetable = new Timetable();

        Group group = new Group("Adult Yoga", Age.ADULT, 60);
        Coach a = new Coach("Ivanov", "I", "I");
        Coach b = new Coach("Sidorov", "S", "S");

        // Ivanov ведёт 3 занятия, Sidorov — 2
        timetable.addNewTrainingSession(new TrainingSession(group, a, DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, a, DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, a, DayOfWeek.SATURDAY, new TimeOfDay(12, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, b, DayOfWeek.FRIDAY, new TimeOfDay(19, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, b, DayOfWeek.SUNDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        assertEquals(2, counts.size(), "Ожидается два тренера в результате подсчёта");
        assertEquals(a, counts.get(0).getCoach(), "Первым должен быть тренер с максимальным количеством занятий");
        assertEquals(3, counts.get(0).getCount());
        assertEquals(b, counts.get(1).getCoach());
        assertEquals(2, counts.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesEmpty() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        assertNotNull(counts);
        assertTrue(counts.isEmpty(), "Ожидается пустой список, если расписание пустое");
    }

    @Test
    void testGetCountByCoachesWithIdenticalCoachObjects() {
        Timetable timetable = new Timetable();

        Group group = new Group("Mixed", Age.ADULT, 50);
        // два разных объекта Coach с одинаковыми полями — должны суммироваться
        Coach c1 = new Coach("Same", "Name", "X");
        Coach c2 = new Coach("Same", "Name", "X");

        timetable.addNewTrainingSession(new TrainingSession(group, c1, DayOfWeek.MONDAY, new TimeOfDay(8, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, c2, DayOfWeek.TUESDAY, new TimeOfDay(8, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, c1, DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0)));

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        assertEquals(1, counts.size(), "Ожидается один тренер после суммирования идентичных объектов");
        assertEquals(3, counts.get(0).getCount(), "Ожидается 3 занятия у тренера");
    }
}
