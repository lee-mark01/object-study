package study.chapter02.step01

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

fun main() {
    // 금액 할인 정책: 순번 1·10이거나 월요일 10:00~11:59, 목요일 10:00~20:59 상영이면 800원 할인
    val avatar = Movie(
        "아바타",
        Duration.ofMinutes(120),
        Money.wons(10000),
        AmountDiscountPolicy(
            Money.wons(800),
            SequenceCondition(1),
            SequenceCondition(10),
            PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
            PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))
        )
    )

    // 비율 할인 정책: 화요일 14:00~16:59, 순번 2, 목요일 10:00~13:59 상영이면 10% 할인
    val titanic = Movie(
        "타이타닉",
        Duration.ofMinutes(180),
        Money.wons(11000),
        PercentDiscountPolicy(
            0.1,
            PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
            SequenceCondition(2),
            PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))
        )
    )

    // 할인 정책 없음: step01에서는 null로 표현하고, Movie가 null 검사로 처리한다.
    val starWars = Movie(
        "스타워즈",
        Duration.ofMinutes(210),
        Money.wons(10000),
        null
    )

    val customer = Customer("홍길동", "customer-1")

    val monday = LocalDateTime.of(2026, 11, 9, 10, 30)
    val tuesday = LocalDateTime.of(2026, 11, 10, 15, 0)
    val wednesday = LocalDateTime.of(2026, 11, 11, 19, 0)

    printReservation("아바타 (월 10:30, 3회차, 2명) - 기간 조건 만족", Screening(avatar, 3, monday), customer, 2)
    printReservation("아바타 (수 19:00, 5회차, 2명) - 조건 불만족", Screening(avatar, 5, wednesday), customer, 2)
    printReservation("타이타닉 (화 15:00, 3회차, 1명) - 기간 조건 만족", Screening(titanic, 3, tuesday), customer, 1)
    printReservation("스타워즈 (수 19:00, 1회차, 3명) - 할인 정책 없음", Screening(starWars, 1, wednesday), customer, 3)
}

private fun printReservation(label: String, screening: Screening, customer: Customer, audienceCount: Int) {
    val reservation = screening.reserve(customer, audienceCount)
    println("$label -> ${reservation.fee}")
}
