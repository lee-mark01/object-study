package study.chapter01.step02;

/*
step02 new: refactor
소극장이 셀러의 사무실에서 티켓을 get 하고 있고, 관객의 가방에 티켓을 넣고, 돈을 차감하고 있다.
티켓이라는 데이터는 셀러의 책임이고, 관객의 가방,돈은 관객의 책임이다.
소극장은 셀러에게 "판매해"라는 메세지를, 셀러는 관객에게 "구매해"라는 메세지를 전달한다.
 */
public class Audience {
    private Bag bag;

    public Audience(Bag bag){
        this.bag = bag;
    }

    public long buy(Ticket ticket) {
        if (bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        } else {
            bag.setTicket(ticket);
            bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }
}
