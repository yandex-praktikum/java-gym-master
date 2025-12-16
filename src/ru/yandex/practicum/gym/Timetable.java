package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }
        TreeMap<TimeOfDay, List<TrainingSession>> dailySchedule = timetable.get(day);

        if (!dailySchedule.containsKey(time)) {
            dailySchedule.put(time, new ArrayList<>());
        }
        List<TrainingSession> sessions = dailySchedule.get(time);


        sessions.add(trainingSession);
    }

    //метод возвращает все расписание на указанный день
    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainigs = timetable.get(dayOfWeek); //сеансы на день

        if (Objects.isNull(trainigs)) {
            return new TreeMap<>();
        }
        return trainigs;
    }

    //метод возвращает сеансы для указанного дня и времени
    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayTrainings = timetable.get(dayOfWeek); //расписание на день

        if (Objects.isNull(dayTrainings)) {
            return new ArrayList<>();
        }
        List<TrainingSession> sessions = dayTrainings.get(timeOfDay);

        if (Objects.isNull(sessions)) {
            return new ArrayList<>();
        }
        return sessions;
    }

    //метод возвращает кол-во тренировок у каждого тренера по убыванию
    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> countByCoaches = new HashMap<>(); //счетчик

        //перебираем все дни в расписании
        for (TreeMap<TimeOfDay, List<TrainingSession>> dailySessions : timetable.values()) {
            //перебираем все времена в один день
            for (List<TrainingSession> sessionsAtTime : dailySessions.values()) {
                //перебираем каждую тренировку в это время
                for (TrainingSession session : sessionsAtTime) {
                    Coach coach = session.getCoach(); //получаем тренера, который ведет эту тренировку
                    //увеличиваем счетчик для этого тренера
                    countByCoaches.put(coach, countByCoaches.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : countByCoaches.entrySet()) {
            Coach coach = entry.getKey();
            int count = entry.getValue();
            result.add(new CounterOfTrainings(coach, count));
        }
        Comparator<CounterOfTrainings> comparator = new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings a, CounterOfTrainings b) {
                return b.getCount() - a.getCount();
            }
        };
        result.sort(comparator);

        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // "строитель" текста

        for (DayOfWeek day : DayOfWeek.values()) {
            if (!timetable.containsKey(day)) {
                continue;
            }
            sb.append(day).append(":\n");
            TreeMap<TimeOfDay, List<TrainingSession>> dailySessions = timetable.get(day);
            for (TimeOfDay time : dailySessions.keySet()) {
                List<TrainingSession> sessions = dailySessions.get(time);
                for (TrainingSession session : sessions) {
                    String title = session.getGroup().getTitle();
                    Coach coach = session.getCoach();
                    sb.append("  ")
                            .append(time)
                            .append(" — ")
                            .append(title)
                            .append(" (")
                            .append(coach)
                            .append(")")
                            .append("\n");
                }
            }
        }
        return sb.toString();
    }
}