package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }
        TimeOfDay time = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);
        if (!daySchedule.containsKey(time)) {
            daySchedule.put(time, new ArrayList<>());
        }
        daySchedule.get(time).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (!timetable.containsKey(dayOfWeek)) {
            return Collections.emptyList();
        }
        List<TrainingSession> sessions = new ArrayList<>();
        for (List<TrainingSession> sessionList : timetable.get(dayOfWeek).values()) {
            sessions.addAll(sessionList);
        }
        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (!timetable.containsKey(dayOfWeek)) {
            return Collections.emptyList();
        }
        TreeMap<TimeOfDay, List<TrainingSession>> schedule = timetable.get(dayOfWeek);
        if (!schedule.containsKey(timeOfDay)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(schedule.get(timeOfDay));
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            for (List<TrainingSession> sessionList : daySchedule.values()) {
                for (TrainingSession session : sessionList) {
                    Coach coach = session.getCoach();
                    coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<Map.Entry<Coach, Integer>> entries = new ArrayList<>(coachCounts.entrySet());
        entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        LinkedHashMap<Coach, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}