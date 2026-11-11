package study.chapter02.step02

import java.math.BigDecimal

// 금액을 Long 같은 원시 타입 대신 객체로 표현해 "금액"이라는 의미와 연산을 한곳에 모은다.
class Money private constructor(
    private val amount: BigDecimal
) {
    companion object {
        val ZERO: Money = wons(0)

        fun wons(amount: Long): Money = Money(BigDecimal.valueOf(amount))

        fun wons(amount: Double): Money = Money(BigDecimal.valueOf(amount))
    }

    fun plus(amount: Money): Money = Money(this.amount.add(amount.amount))

    fun minus(amount: Money): Money = Money(this.amount.subtract(amount.amount))

    fun times(percent: Double): Money = Money(this.amount.multiply(BigDecimal.valueOf(percent)))

    fun isLessThan(other: Money): Boolean = amount < other.amount

    fun isGreaterThanOrEqual(other: Money): Boolean = amount >= other.amount

    // BigDecimal.equals는 scale까지 비교해 1000과 1000.0을 다르게 보므로 compareTo로 값만 비교한다.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Money) return false
        return amount.compareTo(other.amount) == 0
    }

    override fun hashCode(): Int = amount.stripTrailingZeros().hashCode()

    // times(Double)를 거치면 scale이 붙어 "9900.0원"처럼 보이므로 출력할 때만 정리한다.
    override fun toString(): String = "${amount.stripTrailingZeros().toPlainString()}원"
}
