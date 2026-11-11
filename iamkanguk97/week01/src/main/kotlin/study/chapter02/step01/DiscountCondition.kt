package study.chapter02.step01

interface DiscountCondition {
    fun isSatisfiedBy(screening: Screening): Boolean
}
