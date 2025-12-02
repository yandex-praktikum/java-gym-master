package ru.yandex.practicum.gym;

import java.time.DayOfWeek;
import java.util.Objects;

public class TrainingSession {
    private Group group; // ИСПРАВЛЕНО: было ru.yandex.practicum.gym.Group
    private Coach coach;
    private DayOfWeek dayOfWeek;
    private TimeOfDay startTime;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay startTime) {
        this.group = Objects.requireNonNull(group, "Group cannot be null.");
        this.coach = Objects.requireNonNull(coach, "Coach cannot be null.");
        this.dayOfWeek = Objects.requireNonNull(dayOfWeek, "DayOfWeek cannot be null.");
        this.startTime = Objects.requireNonNull(startTime, "Start time cannot be null.");
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSession that = (TrainingSession) o;
        return Objects.equals(group, that.group) &&
                Objects.equals(coach, that.coach) &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, coach, dayOfWeek, startTime);
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "group=" + group.getName() +
                ", coach=" + coach.getName() +
                ", day=" + dayOfWeek +
                ", startTime=" + startTime +
                '}';
    }
}