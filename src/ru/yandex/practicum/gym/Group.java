package ru.yandex.practicum.gym;

import java.util.Objects;

public class Group {
    //название группы
    private String title;
    //тип (взрослая или детская)
    private Age age;
    //длительность (в минутах)
    private int duration;

    public Group(String title, Age age, int duration) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название группы не может быть пустым или null");
        }
        if (age == null) {
            throw new IllegalArgumentException("Некорректные значения возрастной группы");
        }
        if (duration < 1 || duration > 1440) {
            throw new IllegalArgumentException("Некорректные значение длительности занятия (1..1440)");
        }
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) return;
        this.title = title;
    }

    public void setAge(Age age) {
        if (age == null) return;
        this.age = age;
    }

    public void setDuration(int duration) {
        if (duration < 1 || duration > 1440) return;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return duration == group.duration && Objects.equals(title, group.title) && age == group.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, age, duration);
    }
}
