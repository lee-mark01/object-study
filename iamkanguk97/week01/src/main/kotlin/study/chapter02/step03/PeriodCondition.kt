package study.chapter02.step03

import java.time.DayOfWeek
import java.time.LocalTime

class PeriodCondition(
    private val dayOfWeek: DayOfWeek,
    private val startTime: LocalTime,
    private val endTime: LocalTime
) : DiscountCondition {
    override fun isSatisfiedBy(screening: Screening): Boolean {
        val screeningStartTime = screening.getStartTime()
        val screeningTime = screeningStartTime.toLocalTime()

        return screeningStartTime.dayOfWeek == dayOfWeek &&
            startTime <= screeningTime &&
            endTime >= screeningTime
    }
}
