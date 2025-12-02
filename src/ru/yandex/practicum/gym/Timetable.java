package ru.yandex.practicum.gym;

import java.time.DayOfWeek;
import java.util.*;

public class Timetable {
    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> schedule;

    public Timetable() {
        schedule = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new TreeMap<>());
        }
    }

    public boolean addNewTrainingSession(TrainingSession session) {
        Objects.requireNonNull(session, "Training session cannot be null.");

        DayOfWeek day = session.getDayOfWeek();
        TimeOfDay startTime = session.getStartTime();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = schedule.get(day);
        List<TrainingSession> sessionsAtTime = daySchedule
                .computeIfAbsent(startTime, k -> new ArrayList<>());

        if (sessionsAtTime.contains(session)) {
            return false;
        }

        sessionsAtTime.add(session);
        return true;
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        Objects.requireNonNull(dayOfWeek, "DayOfWeek cannot be null.");

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = schedule.get(dayOfWeek);
        if (daySchedule == null) {
            return Collections.emptyMap();
        }

        Map<TimeOfDay, List<TrainingSession>> result = new TreeMap<>();
        for (Map.Entry<TimeOfDay, List<TrainingSession>> entry : daySchedule.entrySet()) {
            result.put(entry.getKey(), Collections.unmodifiableList(new ArrayList<>(entry.getValue())));
        }

        return Collections.unmodifiableMap(result);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay time) {
        Objects.requireNonNull(dayOfWeek, "DayOfWeek cannot be null.");
        Objects.requireNonNull(time, "Time cannot be null.");

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = schedule.get(dayOfWeek);
        if (daySchedule != null) {
            List<TrainingSession> sessions = daySchedule.get(time);
            if (sessions != null) {
                return Collections.unmodifiableList(new ArrayList<>(sessions));
            }
        }
        return Collections.emptyList();
    }

    public List<TrainingSession> getAllTrainingSessionsForDaySorted(DayOfWeek dayOfWeek) {
        Objects.requireNonNull(dayOfWeek, "DayOfWeek cannot be null.");

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = schedule.get(dayOfWeek);
        if (daySchedule == null || daySchedule.isEmpty()) {
            return Collections.emptyList();
        }

        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> sessions : daySchedule.values()) {
            result.addAll(sessions);
        }

        return Collections.unmodifiableList(result);
    }

    public List<CoachTrainingCount> getCountByCoaches() {
        Map<Coach, Integer> coachCountMap = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : schedule.values()) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachCountMap.put(coach, coachCountMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CoachTrainingCount> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCountMap.entrySet()) {
            result.add(new CoachTrainingCount(entry.getKey(), entry.getValue()));
        }

        Collections.sort(result);
        return Collections.unmodifiableList(result);
    }

    public int getTotalNumberOfSessions() {
        return schedule.values().stream()
                .flatMap(dayMap -> dayMap.values().stream())
                .mapToInt(List::size)
                .sum();
    }
}