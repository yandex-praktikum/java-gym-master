package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    //    private Map<Coach, Group> coachGroupMap = new HashMap<>();
//    private List<Map<Coach, Group>> coachGroupList = new ArrayList<>();
//    private Map<TimeOfDay, List<Map<Coach, Group>>> timeMap = new TreeMap<>();
    private Map<DayOfWeek, Map<TimeOfDay, List<Map<Coach, Group>>>> timetable = new HashMap<>();
    private Map<Coach, Integer> coachSessions = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        Coach coach = trainingSession.getCoach();
        Group group = trainingSession.getGroup();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();

        Map<Coach, Group> coachGroupMap = new HashMap<>();
        coachGroupMap.put(coach, group);

        List<Map<Coach, Group>> coachGroupList = timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new ArrayList<>());
        coachGroupList.add(coachGroupMap);

        Map<TimeOfDay, List<Map<Coach, Group>>> timeMap = timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        timeMap.put(timeOfDay, coachGroupList);

        timetable.put(dayOfWeek, timeMap);

        coachSessions.put(coach, coachSessions.getOrDefault(coach, 0) + 1);
    }

    public Map<TimeOfDay, List<Map<Coach, Group>>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<Map<Coach, Group>> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(coachSessions.entrySet());
        list.sort(Comparator.comparing(Map.Entry<Coach, Integer>::getValue).reversed());
        return list;
    }
}
