package ru.yandex.practicum.gym;

public class Group {
    private String title; //название группы
    private Age age; //тип (взрослая или детская)
    private int duration; //длительность (в минутах)

    public Group(String title, Age age, int duration) {
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
}
