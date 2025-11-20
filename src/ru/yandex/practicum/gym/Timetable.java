package ru.yandex.practicum.gym;

import java.util.*;


public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
        for (DayOfWeek d : DayOfWeek.values()) {
            timetable.put(d, new TreeMap<>());
        }
    }

    //Добавляет занятие в расписание.

    public void addNewTrainingSession(TrainingSession trainingSession) {
        Objects.requireNonNull(trainingSession, "trainingSession == null");
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);
        List<TrainingSession> list = dayMap.computeIfAbsent(time, t -> new ArrayList<>());
        list.add(trainingSession);
    }

    //Возвращает все занятия в указанный день, упорядоченные по времени начала.

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        Objects.requireNonNull(dayOfWeek, "dayOfWeek == null");
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null || dayMap.isEmpty()) {
            return Collections.emptyList();
        }
        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> sessionsAtTime : dayMap.values()) {
            result.addAll(sessionsAtTime);
        }
        return Collections.unmodifiableList(result);
    }

    //Возвращает список занятий, начинающихся в указанное время в указанном дне.

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Objects.requireNonNull(dayOfWeek, "dayOfWeek == null");
        Objects.requireNonNull(timeOfDay, "timeOfDay == null");
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) return Collections.emptyList();
        List<TrainingSession> list = dayMap.get(timeOfDay);
        if (list == null || list.isEmpty()) return Collections.emptyList();
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    //Подсчитывает количество занятий в неделю для каждого тренера и возвращает список CounterOfTrainings,
    //отсортированный по убыванию количества занятий.

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counters = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession ts : sessions) {
                    counters.merge(ts.getCoach(), 1, Integer::sum);
                }
            }
        }
        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> e : counters.entrySet()) {
            result.add(new CounterOfTrainings(e.getKey(), e.getValue()));
        }
        Collections.sort(result);
        return Collections.unmodifiableList(result);
    }
}
