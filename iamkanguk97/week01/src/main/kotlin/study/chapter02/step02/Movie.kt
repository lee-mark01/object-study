package study.chapter02.step02

import java.time.Duration

class Movie(
    private val title: String,
    private val runningTime: Duration,
    val fee: Money,
    private val discountPolicy: DiscountPolicy  // 할인 없음은 NoneDiscountPolicy로 표현하므로 null을 허용하지 않는다
) {
    // 할인 금액을 정하는 책임은 항상 DiscountPolicy에게 있다. Movie에 예외 분기가 사라졌다.
    fun calculateMovieFee(screening: Screening): Money =
        fee.minus(discountPolicy.calculateDiscountAmount(screening))
}
