package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private ArrayList<CounterOfTrainings> coachTrainingCountList = new ArrayList<>();

    public ArrayList<CounterOfTrainings> getCoachTrainingCountList() {
        return coachTrainingCountList;
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeekForAdding = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDayForAdding = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(dayOfWeekForAdding)) {
            timetable.put(dayOfWeekForAdding, new TreeMap<>());
            timetable.get(dayOfWeekForAdding).put(timeOfDayForAdding, new ArrayList<>());
        }

        if (!timetable.get(dayOfWeekForAdding).containsKey(timeOfDayForAdding)) {
            timetable.get(dayOfWeekForAdding).put(timeOfDayForAdding, new ArrayList<>());
        }

        timetable.get(dayOfWeekForAdding).get(timeOfDayForAdding).add(trainingSession);

        updateCoachTrainingSessionList(trainingSession);
    }

    private void updateCoachTrainingSessionList(TrainingSession trainingSession) {
        CounterOfTrainings probe = new CounterOfTrainings(trainingSession.getCoach());
        if (!coachTrainingCountList.contains(probe)) {
            probe.addNewTraining();
            coachTrainingCountList.add(probe);
        } else {
            coachTrainingCountList.get(coachTrainingCountList.indexOf(probe)).addNewTraining();
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        List<TrainingSession> trainingSessions = new ArrayList<>();
        if (!timetable.containsKey(dayOfWeek)) {
            return trainingSessions;
        }

        for (TimeOfDay td : timetable.get(dayOfWeek).navigableKeySet()) {
            trainingSessions.addAll(timetable.get(dayOfWeek).get(td));
        }

        return trainingSessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> sessions = timetable.get(dayOfWeek);
        if (sessions == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> list = sessions.get(timeOfDay);
        if (list == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(list);
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        Collections.sort(coachTrainingCountList, new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
                return Integer.compare(o2.getTrainingCount(), o1.getTrainingCount());
            }
        });

        return coachTrainingCountList;
    }
}
