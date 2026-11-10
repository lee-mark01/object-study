package study.chapter01.step05

class TicketSeller(
    private val ticketOffice: TicketOffice
) {
    // TicketOffice를 뒤지지 않고, 관람객에게 티켓을 팔아 달라(sellTicketTo)고만 요청한다
    fun sellTo(audience: Audience) {
        ticketOffice.sellTicketTo(audience)
    }
}
