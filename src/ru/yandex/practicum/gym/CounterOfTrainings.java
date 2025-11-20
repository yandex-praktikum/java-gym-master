package ru.yandex.practicum.gym;

//Класс для хранения пары (тренер, количество занятий).

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private final Coach coach;
    private final int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        int cmp = Integer.compare(o.count, this.count); // по убыванию
        if (cmp != 0) return cmp;
        // детерминированность: при равенстве количества — по фамилии/имени тренера (лексикографически)
        String fullThis = coach.getSurname() + " " + coach.getName() + " " + coach.getMiddleName();
        String fullOther = o.coach.getSurname() + " " + o.coach.getName() + " " + o.coach.getMiddleName();
        return fullThis.compareTo(fullOther);
    }

    @Override
    public String toString() {
        return "{" + coach.getSurname() + " " + coach.getName() + " " + coach.getMiddleName() + "=" + count + "}";
    }
}
