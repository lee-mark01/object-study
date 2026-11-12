package study.chapter02.step04

import java.time.Duration

class Movie(
    private val title: String,
    private val runningTime: Duration,
    val fee: Money,
    private var discountPolicy: DiscountPolicy  // 실행 중에 정책을 갈아 끼울 수 있도록 var로 둔다
) {
    fun calculateMovieFee(screening: Screening): Money =
        fee.minus(discountPolicy.calculateDiscountAmount(screening))

    // 합성: Movie는 할인 정책을 상속받지 않고 필드로 가지므로, 실행 중에도 다른 정책으로 교체할 수 있다.
    // 상속(AmountDiscountMovie, PercentDiscountMovie)이었다면 정책이 클래스에 고정되어 객체를 새로 만들어야 한다.
    fun changeDiscountPolicy(discountPolicy: DiscountPolicy) {
        this.discountPolicy = discountPolicy
    }
}
