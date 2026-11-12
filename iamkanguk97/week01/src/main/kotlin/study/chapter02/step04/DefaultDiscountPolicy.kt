package study.chapter02.step04

abstract class DefaultDiscountPolicy(
    vararg conditions: DiscountCondition
) : DiscountPolicy {
    private val conditions: List<DiscountCondition> = conditions.toList()

    // 템플릿 메서드 패턴: 조건 검사라는 공통 흐름은 부모가 갖고, 할인 금액 계산만 자식에게 맡긴다.
    // Kotlin에서 override 멤버는 기본적으로 다시 재정의할 수 있으므로, final로 막아 자식이 흐름 자체를 바꾸지 못하게 한다.
    final override fun calculateDiscountAmount(screening: Screening): Money {
        for (condition in conditions) {
            if (condition.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening)
            }
        }

        return Money.ZERO
    }

    protected abstract fun getDiscountAmount(screening: Screening): Money
}
