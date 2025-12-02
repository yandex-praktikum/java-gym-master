package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private int capacity;
    private List<Coach> coaches;
    private Age ageCategory;

    public Group(int id, String name, int capacity, Age ageCategory) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be null or empty.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Group capacity cannot be negative.");
        }
        this.ageCategory = Objects.requireNonNull(ageCategory, "Age category cannot be null.");
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.coaches = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Age getAgeCategory() {
        return ageCategory;
    }

    public List<Coach> getCoaches() {
        return Collections.unmodifiableList(coaches);
    }

    public void addCoach(Coach coach) {
        if (coach != null && !coaches.contains(coach)) {
            coaches.add(coach);
        }
    }

    public boolean removeCoach(Coach coach) {
        if (coach != null) {
            return coaches.remove(coach);
        }
        return false;
    }

    public int getNumberOfCoaches() {
        return coaches.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", ageCategory=" + ageCategory +
                ", coaches=" + coaches.size() +
                '}';
    }
}