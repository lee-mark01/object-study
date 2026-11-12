package study.chapter01.step04

class TicketSeller(
    private val ticketOffice: TicketOffice
) {
    // 관람객의 가방을 열지 않고, 티켓을 건네며 구매(buy)만 요청한다
    fun sellTo(audience: Audience) {
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()))
    }
}
