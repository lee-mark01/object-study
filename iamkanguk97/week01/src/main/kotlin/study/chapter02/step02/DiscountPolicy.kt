package study.chapter02.step02

abstract class DiscountPolicy(
    vararg conditions: DiscountCondition
) {
    private val conditions: List<DiscountCondition> = conditions.toList()

    // 템플릿 메서드 패턴: 조건 검사라는 공통 흐름은 부모가 갖고, 할인 금액 계산만 자식에게 맡긴다.
    fun calculateDiscountAmount(screening: Screening): Money {
        for (condition in conditions) {
            if (condition.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening)
            }
        }

        return Money.ZERO
    }

    protected abstract fun getDiscountAmount(screening: Screening): Money
}
