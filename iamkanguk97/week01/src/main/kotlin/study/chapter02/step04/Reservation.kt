package study.chapter02.step04

class Reservation(
    private val customer: Customer,
    private val screening: Screening,
    val fee: Money,
    private val audienceCount: Int
)
