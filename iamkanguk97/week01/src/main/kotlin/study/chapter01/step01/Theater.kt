package study.chapter01.step01

class Theater(
    private val ticketSeller: TicketSeller
) {
    // Audience와 TicketSeller에는 메서드가 없고, 데이터만 담고 있는 상자임 -> "자율성이 없다"
    fun enter(audience: Audience) {
        if (audience.bag.hasInvitation()) {  // Theater가 Audience의 Bag을 열어서 Invitation이 있나 확인
            val ticket = ticketSeller.ticketOffice.getTicket()  // Theater가 TicketOffice에서 Ticket을 꺼냄
            audience.bag.ticket = ticket  // Theater가 Audience의 Bag에 Ticket을 넣음
        } else {
            val ticket = ticketSeller.ticketOffice.getTicket()
            audience.bag.minusAmount(ticket.fee)  // Theater가 Audience의 Bag에서 돈을 꺼냄
            ticketSeller.ticketOffice.plusAmount(ticket.fee)  // Theater가 TicketOffice에 돈을 넣음
            audience.bag.ticket = ticket
        }
    }
}
