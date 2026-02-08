package ru.yandex.practicum.gym;

import java.util.*;
import java.util.stream.Collectors;

public class TimeTable {

    private Map<DayOfWeek, List<TrainingSession>> timetable = new HashMap<>(); // ключ - день недели, значение - список занятий на этот день.

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        if (!timetable.containsKey(day)) {
            timetable.put(day, new ArrayList<>());
        }
        List<TrainingSession> sessions = timetable.get(day);
        sessions.add(trainingSession);
        sessions.sort(Comparator.comparing(TrainingSession::getTimeOfDay));
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {  // возвращаем список занятий на указанный день недели
        return timetable.getOrDefault(dayOfWeek, new ArrayList<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> sessionsForDay = getTrainingSessionsForDay(dayOfWeek);
        List<TrainingSession> result = new ArrayList<>();
        for (TrainingSession session : sessionsForDay) {
            if (session.getTimeOfDay().equals(timeOfDay)) {
                result.add(session);
            }
        }
        return result;
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> countByCoaches = new HashMap<>();
        for (List<TrainingSession> sessions : timetable.values()) {
            for (TrainingSession session : sessions) {
                Coach coach = session.getCoach();
                countByCoaches.put(coach, countByCoaches.getOrDefault(coach, 0) + 1);
            }
        }
        return countByCoaches.entrySet()
                .stream()
                .sorted(Map.Entry.<Coach, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
