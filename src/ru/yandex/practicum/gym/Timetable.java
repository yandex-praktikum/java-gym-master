package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, List<TrainingSession>> trainingsByDay;

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> trainingsByTime;

    public Timetable() {
        this.trainingsByDay = new HashMap<>();
        this.trainingsByTime = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();

        List<TrainingSession> dayTrainings = trainingsByDay.computeIfAbsent(day, d -> new ArrayList<>());

        dayTrainings.add(trainingSession);

        dayTrainings.sort(Comparator.comparing(TrainingSession::getTimeOfDay));

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = trainingsByTime.computeIfAbsent(day, d -> new TreeMap<>());

        TimeOfDay time = trainingSession.getTimeOfDay();

        daySchedule.computeIfAbsent(time, t -> new ArrayList<>()).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return trainingsByDay.getOrDefault(dayOfWeek, Collections.emptyList());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> schedule = trainingsByTime.get(dayOfWeek);
        if (schedule == null) {
            return Collections.emptyList();
        }
        return schedule.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();
        for (List<TrainingSession> trainingSessions : trainingsByDay.values()) {
            for (TrainingSession session : trainingSessions) {
                Coach coach = session.getCoach();
                coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
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