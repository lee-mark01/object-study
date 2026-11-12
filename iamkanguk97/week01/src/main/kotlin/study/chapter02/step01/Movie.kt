package study.chapter02.step01

import java.time.Duration

class Movie(
    private val title: String,
    private val runningTime: Duration,
    val fee: Money,
    private val discountPolicy: DiscountPolicy?  // 할인 정책이 없는 영화를 표현하려고 null을 허용
) {
    fun calculateMovieFee(screening: Screening): Money {
        // 문제: "할인 정책이 없는 경우"를 예외 케이스로 취급해 Movie가 직접 분기한다.
        // 할인 금액을 결정하는 책임은 DiscountPolicy에 있는데, 이 경우만 Movie가 떠맡아 협력의 일관성이 깨진다.
        if (discountPolicy == null) {
            return fee
        }

        return fee.minus(discountPolicy.calculateDiscountAmount(screening))
    }
}
