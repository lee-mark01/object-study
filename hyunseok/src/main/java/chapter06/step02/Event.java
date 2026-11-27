package chapter06.step02;

import java.time.Duration;
import java.time.LocalDateTime;

public class Event {
    private String subject;
    private LocalDateTime from;
    private Duration duration;

    public Event(String subject, LocalDateTime from, Duration duration) {
        this.subject = subject;
        this.from = from;
        this.duration = duration;
    }

    // 인자로 전달된 반복일정의 내부 상태가 이벤트의 내부 상태와 하나라도 같지 않다면 false 반환
    public boolean isSatisfied(RecurringSchedule schedule) {
        if(from.getDayOfWeek() != schedule.getDayOfWeek() ||
           !from.toLocalTime().equals(schedule.getFrom()) ||
           !duration.equals(schedule.getDuration())) {
            return false;
        }

        return true;
    }

    // Event의 상태 조건을 만족 시키도록 변경하는 메서드
    public void reschedule(RecurringSchedule schedule) {
        from = LocalDateTime.of(from.toLocalDate().plusDays(daysDistance(schedule)), schedule.getFrom());
        duration = schedule.getDuration();
    }

    private long daysDistance(RecurringSchedule schedule) {
        return schedule.getDayOfWeek().getValue() - from.getDayOfWeek().getValue();
    }
}
