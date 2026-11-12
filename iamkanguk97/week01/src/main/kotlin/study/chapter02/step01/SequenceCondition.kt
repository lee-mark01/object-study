package study.chapter02.step01

class SequenceCondition(
    private val sequence: Int
) : DiscountCondition {
    override fun isSatisfiedBy(screening: Screening): Boolean = screening.isSequence(sequence)
}
