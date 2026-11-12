package study.chapter02.step04

interface DiscountCondition {
    fun isSatisfiedBy(screening: Screening): Boolean
}
