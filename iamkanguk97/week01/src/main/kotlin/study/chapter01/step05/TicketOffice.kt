package study.chapter01.step05

class TicketOffice(
    private var amount: Long,
    vararg tickets: Ticket
) {
    private val tickets: MutableList<Ticket> = tickets.toMutableList()

    // TicketOffice의 자율성은 높아졌지만, 대신 Audience에 대한 새 의존이 생겼다 (트레이드오프)
    fun sellTicketTo(audience: Audience) {
        plusAmount(audience.buy(getTicket()))
    }

    // sellTicketTo 내부에서만 쓰이므로 private으로 숨김
    private fun getTicket(): Ticket = tickets.removeAt(0)

    fun minusAmount(amount: Long) {
        this.amount -= amount
    }

    private fun plusAmount(amount: Long) {
        this.amount += amount
    }
}
