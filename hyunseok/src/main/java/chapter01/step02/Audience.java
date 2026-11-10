package chapter01.step02;

/**
 * 관람객, 가방을 소지할 수 있음
 */
public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    /**
     * 가방을 확인해 초대장을 가지고 있다면 요금 지불없이 티켓을 받음
     * 초대장을 가지고 있지 않다면 가방에서 요금을 지불해 티켓을 받음
     * @param ticket
     * @return
     */
    public Long buy(Ticket ticket) {
        if(bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        } else {
            bag.setTicket(ticket);
            bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }
}

