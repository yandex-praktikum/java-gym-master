package ru.yandex.practicum.gym;

import java.util.Objects;

public class CoachTrainingCount implements Comparable<CoachTrainingCount> {
    private final Coach coach;
    private final int trainingCount;

    public CoachTrainingCount(Coach coach, int trainingCount) {
        this.coach = Objects.requireNonNull(coach, "Coach cannot be null");
        if (trainingCount < 0) {
            throw new IllegalArgumentException("Training count cannot be negative.");
        }
        this.trainingCount = trainingCount;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    @Override
    public int compareTo(CoachTrainingCount other) {
        if (other == null) return -1;
        int countCompare = Integer.compare(other.trainingCount, this.trainingCount);
        if (countCompare != 0) {
            return countCompare;
        }
        return this.coach.getName().compareTo(other.coach.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachTrainingCount that = (CoachTrainingCount) o;
        return trainingCount == that.trainingCount &&
                Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, trainingCount);
    }

    @Override
    public String toString() {
        return coach.getName() + ": " + trainingCount + " тренировок";
    }
}