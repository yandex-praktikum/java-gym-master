package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, TrainingSession> trainingInDay;

        if (!timetable.containsKey(trainingSession.getDayOfWeek())) {
            trainingInDay = new TreeMap<>();
            trainingInDay.put(trainingSession.getTimeOfDay(), trainingSession);
        } else {
            trainingInDay = timetable.get(trainingSession.getDayOfWeek());
            trainingInDay.put(trainingSession.getTimeOfDay(), trainingSession);
        }
        timetable.put(trainingSession.getDayOfWeek(), trainingInDay);
    }

    public TreeMap<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)'
        return timetable.get(dayOfWeek);
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> trainingsForCoach = new HashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> entry : timetable.entrySet()) {
            TreeMap<TimeOfDay, TrainingSession> trainingsInDay = entry.getValue();

            for (TrainingSession trainingSession : trainingsInDay.values()) {
                if (!trainingsForCoach.containsKey(trainingSession.getCoach())) {
                    trainingsForCoach.put(trainingSession.getCoach(), 1);
                } else {
                    trainingsForCoach.put(trainingSession.getCoach(), trainingsForCoach.get(trainingSession.getCoach()) + 1);
                }
            }
        }

        List<CounterOfTrainings> listCounterOfTrainings = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : trainingsForCoach.entrySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(entry.getKey(), entry.getValue());
            listCounterOfTrainings.add(counterOfTrainings);
        }
        listCounterOfTrainings.sort(CounterOfTrainings::compareTo);

        Map<Coach, Integer> sortedMap = new HashMap<>();
        for (CounterOfTrainings counterOfTrainings : listCounterOfTrainings) {
            sortedMap.put(counterOfTrainings.getCoach(), counterOfTrainings.getTrainingsCount());
        }

        return sortedMap;
    }
}
