package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private int countOfTrainings;

    public CounterOfTrainings(Coach coach, int countOfTrainings) {
        if (coach == null) {
            throw new IllegalArgumentException("Тренер не должен быть null");
        }
        if (countOfTrainings < 0) {
            throw new IllegalArgumentException("Счетчик тренировок не должен быть отрицательным");
        }
        this.coach = coach;
        this.countOfTrainings = countOfTrainings;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCountOfTrainings() {
        return countOfTrainings;
    }

    public void setCoach(Coach coach) {
        if (coach == null) return;
        this.coach = coach;
    }

    public void setCountOfTrainings(int countOfTrainings) {
        if (countOfTrainings < 0) return;
        this.countOfTrainings = countOfTrainings;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coach);
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return o.countOfTrainings - this.countOfTrainings;
    }
}
