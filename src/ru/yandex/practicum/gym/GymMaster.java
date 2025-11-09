package ru.yandex.practicum.gym;

import java.util.ArrayList;

public class GymMaster {

    public static void main(String[] args) {
        Coach coach1 = new Coach("Иванов", "Иван", "Иваныч");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        Group group1 = new Group("Детская гимнастика", Age.CHILD, 60);
        Group group2 = new Group("Йога", Age.ADULT, 90);

        Timetable timetable = new Timetable();

// Добавляем две тренировки на одно и то же время
        timetable.addNewTrainingSession(new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach2, DayOfWeek.MONDAY, new TimeOfDay(10, 20)));

// Получаем список тренировок в понедельник в 10:00
        ArrayList<TrainingSession> monday10 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        System.out.println("Количество занятий в понедельник в 10:00: " + monday10.size());

    }
}
