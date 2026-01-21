package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, List<TrainingSession>> sessions = new EnumMap<>(DayOfWeek.class);

    public void addNewTrainingSession(TrainingSession trainingSession) {
        List<TrainingSession> daySessions = sessions.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new ArrayList<>());
        daySessions.add(trainingSession);
        Collections.sort(daySessions, Comparator.comparing(TrainingSession::getTimeOfDay));
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return sessions.getOrDefault(dayOfWeek, Collections.emptyList());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> daySessions = sessions.getOrDefault(dayOfWeek, Collections.emptyList());

        List<TrainingSession> result = new ArrayList<>();

        TrainingSession searchKey = new TrainingSession(null, null, dayOfWeek, timeOfDay);
        int index = Collections.binarySearch(daySessions, searchKey, Comparator.comparing(TrainingSession::getTimeOfDay));

        if (index < 0) {
             return result;
        }

        int firstIndex = index;
        while (firstIndex > 0 && daySessions.get(firstIndex - 1).getTimeOfDay().compareTo(timeOfDay) == 0) {
            firstIndex--;
        }

        int lastIndex = index;
        while (lastIndex < daySessions.size() - 1 && daySessions.get(lastIndex + 1).getTimeOfDay().compareTo(timeOfDay) == 0) {
            lastIndex++;
        }

        for (int i = firstIndex; i <= lastIndex; i++) {
            result.add(daySessions.get(i));
        }

        return result;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();

        for (List<TrainingSession> daySessions : sessions.values()) {
            for (TrainingSession session : daySessions) {
                coachCounts.merge(session.getCoach(), 1, Integer::sum);
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        result.sort((c1, c2) -> Integer.compare(c2.getCount(), c1.getCount()));

        return result;
    }
}
