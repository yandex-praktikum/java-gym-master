package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Timetable {

    protected Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        timetable.putIfAbsent(day, new TreeMap<>());

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);
        dayMap.putIfAbsent(time, new ArrayList<>());
        List<TrainingSession> listOfTrainingSessions = dayMap.get(time);
        listOfTrainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> result = new TreeMap<>();
        TreeMap<TimeOfDay, List<TrainingSession>> dayData = timetable.get(dayOfWeek);

        if (dayData != null) {
            for (Map.Entry<TimeOfDay, List<TrainingSession>> entry : dayData.entrySet()) {
                result.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null || !dayMap.containsKey(timeOfDay)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(dayMap.get(timeOfDay));
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> coachCountMap = new HashMap<>();

        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> dayEntry : timetable.entrySet()) {
            TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = dayEntry.getValue();

            for (Map.Entry<TimeOfDay, List<TrainingSession>> timeEntry : daySchedule.entrySet()) {
                List<TrainingSession> sessions = timeEntry.getValue();

                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachCountMap.put(coach, coachCountMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<Map.Entry<Coach, Integer>> sortedList = new ArrayList<>(coachCountMap.entrySet());

        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return sortedList;
    }
}