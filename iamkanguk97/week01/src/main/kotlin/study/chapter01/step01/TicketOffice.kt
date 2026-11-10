package study.chapter01.step01

class TicketOffice(
    private var amount: Long,
    vararg tickets: Ticket
) {
    private val tickets: MutableList<Ticket> = tickets.toMutableList()

    fun getTicket(): Ticket = tickets.removeAt(0)

    fun minusAmount(amount: Long) {
        this.amount -= amount
    }

    fun plusAmount(amount: Long) {
        this.amount += amount
    }
}
