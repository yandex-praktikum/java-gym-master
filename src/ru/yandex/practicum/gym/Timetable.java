package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, HashSet<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        if (trainingSession == null) return;
        TreeMap<TimeOfDay, HashSet<TrainingSession>> timetableByDay = timetable.getOrDefault(trainingSession.getDayOfWeek(), new TreeMap<>());
        HashSet<TrainingSession> trainingSessionsByTimeOfDay = timetableByDay.getOrDefault(trainingSession.getTimeOfDay(), new HashSet<>());
        trainingSessionsByTimeOfDay.add(trainingSession);
        timetableByDay.put(trainingSession.getTimeOfDay(), trainingSessionsByTimeOfDay);
        timetable.put(trainingSession.getDayOfWeek(), timetableByDay);
    }

    public TreeMap<TimeOfDay, HashSet<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public HashSet<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, HashSet<TrainingSession>> timetableByDay = timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        return timetableByDay.getOrDefault(timeOfDay, new HashSet<TrainingSession>());
    }
}
