package study.chapter01.step03

class Audience(
    // private으로 바꿔서 외부(TicketSeller)가 Bag을 직접 열지 못하게 캡슐화
    private val bag: Bag
) {
    // 지불한 금액을 반환: 초대장이 있으면 0, 없으면 티켓 가격
    fun buy(ticket: Ticket): Long {
        if (bag.hasInvitation()) {
            bag.ticket = ticket
            return 0L
        }
        bag.minusAmount(ticket.fee)
        bag.ticket = ticket
        return ticket.fee
    }
}
