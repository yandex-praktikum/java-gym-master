package ru.yandex.practicum.gym;

import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

    //часы (от 0 до 23)
    private int hours;
    //минуты (от 0 до 59)
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    /*@Override
    public int compareTo(TimeOfDay o) {
        if (hours != o.hours) return hours - o.hours;
        return minutes - o.minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }*/

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public int hashCode(){
        return 100*hours + minutes;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return this.hours==timeOfDay.hours && this.minutes==timeOfDay.minutes;
    }

    @Override
    public int compareTo(TimeOfDay o) {
        return this.hours*100+this.minutes - o.hours*100+o.minutes;
    }

    @Override
    public String toString(){
        String minutesStr;
        if (minutes < 10) {
            minutesStr = "0"+minutes;
        }
        else {
            minutesStr = "" + minutes;
        }
        return "Время начала занятия " + hours + ":" +  minutesStr;
    }
}
