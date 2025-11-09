package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek,TreeMap<TimeOfDay,ArrayList<TrainingSession>>> timetable;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании

        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (timetable == null) { //если расписание еще не создано
            timetable = new HashMap<>();
        }
        //список тренировок за конкретный день
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> dayTimeTable = timetable.get(dayOfWeek);
        if (dayTimeTable == null) {
            dayTimeTable = new TreeMap<>();
            timetable.put(dayOfWeek, dayTimeTable);
        }
        //список тренировок за конкретное время timeOfDay за день dayOfWeek
        ArrayList<TrainingSession> trainingSessionsAtTime = dayTimeTable.get(timeOfDay);
        if (trainingSessionsAtTime == null) {
            trainingSessionsAtTime = new ArrayList<>();
            dayTimeTable.put(timeOfDay, trainingSessionsAtTime);
        }
        trainingSessionsAtTime.add(trainingSession);

    }

    public TreeMap<TimeOfDay,ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //сложность должна быть О(1)
        if (timetable == null) {
            return new TreeMap<>();
        }
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());

    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //сложность должна быть О(1)
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> dayTreeMap = timetable.get(dayOfWeek);
        if (dayTreeMap == null) {
            return new ArrayList<>();
        }
        return dayTreeMap.getOrDefault(timeOfDay, new ArrayList<>());
    }
}
