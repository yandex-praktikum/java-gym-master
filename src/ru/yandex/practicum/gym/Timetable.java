package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {


    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> thisDayTraining = timetable.get(dayOfWeek);
        if (thisDayTraining == null) {
            thisDayTraining = new TreeMap<>();
            timetable.put(dayOfWeek, thisDayTraining);
        }

        List<TrainingSession> thisDayTrainingSection = thisDayTraining.get(timeOfDay);
        if (thisDayTrainingSection == null) {
            thisDayTrainingSection = new ArrayList<>();
            thisDayTraining.put(timeOfDay, thisDayTrainingSection);
        }
        trainingSession.getCoach().addTraining();
        thisDayTrainingSection.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> trainigsForDay = timetable.get(dayOfWeek);
        if (trainigsForDay == null) {
            trainigsForDay = new TreeMap<>();
        }
        return trainigsForDay;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainigsForDay = timetable.get(dayOfWeek);
        if (trainigsForDay == null) {
            return new ArrayList<>();
        }
        List<TrainingSession> thisTimeTrainingSection = trainigsForDay.get(timeOfDay);
        if (thisTimeTrainingSection == null) {
            return new ArrayList<>();
        }
        return thisTimeTrainingSection;
    }

    public ArrayList<Coach> countOfTrainings() {
        ArrayList<Coach> countOfTrainings = new ArrayList<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayOfWeek : timetable.values()) {
            for (List<TrainingSession> timeOfDay : dayOfWeek.values()) {
                for (TrainingSession trainingSession : timeOfDay) {
                    if (!countOfTrainings.contains(trainingSession.getCoach())) {
                        countOfTrainings.add(trainingSession.getCoach());
                    }
                }
            }
        }
        countOfTrainings.sort((c1, c2) -> Integer.compare(c2.getCountOfTrainings(), c1.getCountOfTrainings()));
        return countOfTrainings;
    }
}
