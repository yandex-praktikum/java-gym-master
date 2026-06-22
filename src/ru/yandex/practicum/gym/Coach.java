package ru.yandex.practicum.gym;

import java.util.Objects;

public class Coach {

    //фамилия
    private String surname;
    //имя
    private String name;
    //отчество
    private String middleName;

    public Coach(String surname, String name, String middleName) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым или null");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Фамилия не может быть пустым или null");
        }
        if (middleName == null) {
            throw new IllegalArgumentException("Отчество не может быть null");
        }
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(surname, coach.surname) && Objects.equals(name, coach.name) && Objects.equals(middleName, coach.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, middleName);
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isBlank()) return;
        this.surname = surname;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) return;
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        if (middleName == null) return;
        this.middleName = middleName;
    }
}
