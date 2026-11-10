package study.chapter01.step02

class TicketSeller(
    // private으로 바꿔서 외부(Theater)가 TicketOffice에 직접 접근하지 못하게 캡슐화
    private val ticketOffice: TicketOffice
) {
    fun sellTo(audience: Audience) {
        if (audience.bag.hasInvitation()) {
            val ticket = ticketOffice.getTicket()
            audience.bag.ticket = ticket
        } else {
            val ticket = ticketOffice.getTicket()
            audience.bag.minusAmount(ticket.fee)
            ticketOffice.plusAmount(ticket.fee)
            audience.bag.ticket = ticket
        }
    }
}
