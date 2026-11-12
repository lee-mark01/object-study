package study.chapter02.step03

interface DiscountCondition {
    fun isSatisfiedBy(screening: Screening): Boolean
}
