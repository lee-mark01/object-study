package study.chapter01.step04

class Theater(
    private val ticketSeller: TicketSeller
) {
    // Theater는 TicketSeller의 인터페이스(sellTo)에만 의존하고, TicketOffice의 존재는 모른다
    fun enter(audience: Audience) {
        ticketSeller.sellTo(audience)
    }
}
