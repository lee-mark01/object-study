package chapter05.step02;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class PeriodCondition implements DiscountCondition{

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isSatisfiedBy(Screening screening) {
        return dayOfWeek.equals(screening.getWhenScreend().getDayOfWeek()) &&
                startTime.compareTo(screening.getWhenScreend().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getWhenScreend().toLocalTime()) >= 0;
    }
}
