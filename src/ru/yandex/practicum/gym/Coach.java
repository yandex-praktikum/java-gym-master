package ru.yandex.practicum.gym;

import java.util.Objects;

public class Coach {
    private int id;
    private String name;

    public Coach(int id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("Coach ID cannot be negative.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Coach name cannot be null or empty.");
        }
        this.id = id;
        this.name = name.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return id == coach.id && Objects.equals(name, coach.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Coach{id=" + id + ", name='" + name + "'}";
    }
}