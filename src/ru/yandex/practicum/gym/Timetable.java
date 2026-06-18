package ru.yandex.practicum.gym;

import java.util.*;
import java.util.stream.Collectors;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {

            if (timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
                timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
            } else {
                timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), new ArrayList<>(List.of(trainingSession)));
            }
        } else {
            timetable.put(
                    trainingSession.getDayOfWeek(),
                    new TreeMap<>(Map.of(trainingSession.getTimeOfDay(), new ArrayList<>(List.of(trainingSession))))
            );
        }
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable
                .getOrDefault(dayOfWeek, new TreeMap<>())
                .getOrDefault(timeOfDay, new ArrayList<>());
    }

    public HashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> counters = new HashMap<>();

        // Пришлось немного погуглить, потому что не хотелось делать три вложенных цикла для обхода списка тренировок
        List<TrainingSession> allTrainingSessions = timetable
                .values()
                .stream()
                .flatMap(day -> day.values().stream())
                .flatMap(sessions -> sessions.stream())
                .toList();

        for (TrainingSession session : allTrainingSessions) {
            Coach coach = session.getCoach();

            Integer newCount = counters.containsKey(coach) ? counters.getOrDefault(coach, 0) + 1 : 1;
            counters.put(coach, newCount);
        }

        return counters.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (counter1, counter2) -> counter1, // считаем, что тренеры у нас уникальные, и коллизий не будет, поэтому тут просто оставляем первый попавшийся счетчик
                        LinkedHashMap::new
                ));
    }
}
