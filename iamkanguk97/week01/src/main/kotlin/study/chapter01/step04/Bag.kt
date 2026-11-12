package study.chapter01.step04

class Bag(
    private val invitation: Invitation?,
    private var amount: Long
) {
    // private으로 바꿔서 외부(Audience)가 티켓을 직접 넣지 못하게 캡슐화
    private var ticket: Ticket? = null

    constructor(amount: Long) : this(null, amount)

    // 책은 hold(보관하다)라는 이름을 쓰지만, 돈을 빼고 금액을 반환하는 일이 이름에 드러나지 않아
    // "티켓값을 치르고 치른 금액을 알려준다"는 의미의 payFor로 바꿈 (초대장이 있으면 0원을 치른 것)
    fun payFor(ticket: Ticket): Long {
        if (hasInvitation()) {
            this.ticket = ticket
            return 0L
        }
        this.ticket = ticket
        minusAmount(ticket.fee)
        return ticket.fee
    }

    // payFor 내부에서만 쓰이므로 private으로 숨김
    private fun hasInvitation(): Boolean = invitation != null

    fun hasTicket(): Boolean = ticket != null

    private fun minusAmount(amount: Long) {
        this.amount -= amount
    }

    fun plusAmount(amount: Long) {
        this.amount += amount
    }
}
