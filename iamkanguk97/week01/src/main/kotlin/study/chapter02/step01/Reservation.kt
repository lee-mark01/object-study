package study.chapter02.step01

class Reservation(
    private val customer: Customer,
    private val screening: Screening,
    val fee: Money,
    private val audienceCount: Int
)
