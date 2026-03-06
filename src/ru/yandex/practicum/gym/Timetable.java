package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.getOrDefault(trainingSession.getDayOfWeek(), new TreeMap<>());
        List<TrainingSession> listOfSessions = daySchedule.computeIfAbsent(trainingSession.getTimeOfDay(), key -> new ArrayList<>());
        listOfSessions.add(trainingSession);
        timetable.put(trainingSession.getDayOfWeek(), daySchedule);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }


    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        TreeMap<TimeOfDay, List<TrainingSession>> weekSchedule = timetable.get(dayOfWeek);
        return weekSchedule.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<CounterOfTrainingsByCoach> getCountByCoaches() {
        HashMap<String, Integer> trainingSessionsByCoaches = new HashMap<>();

        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> dayEntry : timetable.entrySet()) {
            // заходим в дни недели
            DayOfWeek day = dayEntry.getKey();
            TreeMap<TimeOfDay, List<TrainingSession>> timeMap = dayEntry.getValue();

            for (Map.Entry<TimeOfDay, List<TrainingSession>> timeEntry : timeMap.entrySet()) {
                // заходим во время
                TimeOfDay time = timeEntry.getKey();
                List<TrainingSession> sessionsByTime = timeEntry.getValue();

                for (TrainingSession singleSession : sessionsByTime) {
                    //заходим в тренировку
                    String coachSurname = singleSession.getCoach().getSurname();
                    trainingSessionsByCoaches.put(coachSurname, trainingSessionsByCoaches.getOrDefault(coachSurname, 0) + 1);
                }
            }
        }

        List<CounterOfTrainingsByCoach> counterOfTrainingsByCoaches = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : trainingSessionsByCoaches.entrySet()) {
            counterOfTrainingsByCoaches.add(new CounterOfTrainingsByCoach(entry.getKey(), entry.getValue()));
        }

        counterOfTrainingsByCoaches.sort((a,b) -> Integer.compare(b.getSessionsCount(), a.getSessionsCount()));
        return counterOfTrainingsByCoaches;
    }
}