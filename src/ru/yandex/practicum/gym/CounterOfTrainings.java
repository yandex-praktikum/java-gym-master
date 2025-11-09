package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings {
    private int trainingCount = 0;
    private Coach coach;

    public CounterOfTrainings(Coach coach) {
        this.coach = coach;
    }

    public void addNewTraining() {
        trainingCount++;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CounterOfTrainings that = (CounterOfTrainings) o;
        return Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach);
    }

    @Override
    public String toString() {
        return "{кол-во тренировок=" + trainingCount +
                ", " + coach +
                '}';
    }
}
