package chapter01.step04;

/**
 * 관람객이 소지하는 가방(돈, 초대장, 티켓 등)
 * 관람객의 상태( 이벤트 당첨 O or X)
 */
public class Bag {
    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    /**
     * 이번트에 당첨이 안된 관람객
     * @param amount
     */
    public Bag(Long amount) {
        this(null, amount);
    }

    /**
     * 이벤트에 당첨된 관람객
     * @param invitation
     * @param amount
     */
    public Bag(Invitation invitation, long amount) {
        this.invitation = invitation;
        this.amount = amount;
    }

    /**
     * hold()가 하는 일
     * 1. 초대장 검사
     * 2. ticket 받기
     * 3. 금액 정산
     * @param ticket
     * @return
     */
    public Long hold(Ticket ticket) {
        if(hasInvitation()) {
            setTicket(ticket);
            return 0L;
        } else {
            setTicket(ticket);
            minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    /* 관람객이 초대장을 소지하고 있다면 true를 반환 */
    private boolean hasInvitation() {
        return invitation != null;
    }

    /* 관람객이 티켃을 소지하고 있다면 true를 반환 */
    private boolean hasTicket() {
        return ticket != null;
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /* 소지한 현금을 감소 */
    private void minusAmount(Long amount) {
        this.amount -= amount;
    }

    /* 소지한 현금을 증가 */
    private void plusAmount(Long amount) {
        this.amount += amount;
    }
}
