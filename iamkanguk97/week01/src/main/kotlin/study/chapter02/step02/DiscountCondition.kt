package study.chapter02.step02

interface DiscountCondition {
    fun isSatisfiedBy(screening: Screening): Boolean
}
