package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainingsByCoach {

    private String coach;
    private Integer sessionsCount;

    public CounterOfTrainingsByCoach(String coach, Integer sessionsCount) {
        this.coach = coach;
        this.sessionsCount = sessionsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainingsByCoach that = (CounterOfTrainingsByCoach) o;
        return Objects.equals(coach, that.coach) && Objects.equals(sessionsCount, that.sessionsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, sessionsCount);
    }

    public String getCoach() {
        return coach;
    }

    public Integer getSessionsCount() {
        return sessionsCount;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public void setSessionsCount(Integer sessionsCount) {
        this.sessionsCount = sessionsCount;
    }

    @Override
    public String toString() {
        return "CounterOfTrainingsByCoach{" +
                "coach=" + coach +
                ", sessionsCount=" + sessionsCount +
                '}';
    }
}
