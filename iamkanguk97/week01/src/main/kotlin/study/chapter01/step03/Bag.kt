package study.chapter01.step03

class Bag(
    private val invitation: Invitation?,
    private var amount: Long
) {
    var ticket: Ticket? = null

    constructor(amount: Long) : this(null, amount)

    fun hasInvitation(): Boolean = invitation != null

    fun hasTicket(): Boolean = ticket != null

    fun minusAmount(amount: Long) {
        this.amount -= amount
    }

    fun plusAmount(amount: Long) {
        this.amount += amount
    }
}
