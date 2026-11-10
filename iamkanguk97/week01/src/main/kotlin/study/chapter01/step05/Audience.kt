package study.chapter01.step05

class Audience(
    private val bag: Bag
) {
    // Bag을 뒤지지 않고, 티켓을 건네며 값을 치러 달라(payFor)고만 요청한다
    fun buy(ticket: Ticket): Long = bag.payFor(ticket)
}
