package chapter01.step05;

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
     * received()는 티켓을 받으면 처리하는 프로세스만 가진다.
     * @param ticket
     * @return
     */
    public Long receive(Ticket ticket) {
        Long paid = paidFor(ticket); // 얼마를 낼지
        pay(paid);
        setTicket(ticket);
        return paid;
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


    /**
     * step05 추가
     */
    // 지불할 금액 판단
    private Long paidFor(Ticket ticket) {
        return hasInvitation() ? 0L : ticket.getFee();
    }

    // 잔액 검사 후 지불
    private void pay(Long paid) {
        if(amount < paid) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }

        minusAmount(paid);
    }
}
