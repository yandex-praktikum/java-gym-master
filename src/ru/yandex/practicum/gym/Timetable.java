package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

public class Timetable {
    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        timetable.put(DayOfWeek.MONDAY, new TreeMap<>());
        timetable.put(DayOfWeek.TUESDAY, new TreeMap<>());
        timetable.put(DayOfWeek.WEDNESDAY, new TreeMap<>());
        timetable.put(DayOfWeek.THURSDAY, new TreeMap<>());
        timetable.put(DayOfWeek.FRIDAY, new TreeMap<>());
        timetable.put(DayOfWeek.SATURDAY, new TreeMap<>());
        timetable.put(DayOfWeek.SUNDAY, new TreeMap<>());
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> curTreeMap = timetable.get(trainingSession.getDayOfWeek());
        ArrayList<TrainingSession> listTrainingSession;
        if (curTreeMap.containsKey(trainingSession.getTimeOfDay())){
            listTrainingSession = curTreeMap.get(trainingSession.getTimeOfDay());
        }
        else {
            listTrainingSession = new ArrayList<>();
            curTreeMap.put(trainingSession.getTimeOfDay(),listTrainingSession);
        }
        listTrainingSession.add(trainingSession);
    }

    public TreeMap<TimeOfDay,ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public void getTrainingSessionsForDayToString(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        System.out.println("Расписание для дня недели " + dayOfWeek + ":");
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> curTreeMap = timetable.get(dayOfWeek);
        for (ArrayList<TrainingSession> trainingSession : curTreeMap.values()){
            System.out.println(trainingSession);
        }
    }
}
