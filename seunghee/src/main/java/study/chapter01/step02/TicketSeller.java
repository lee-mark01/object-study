package study.chapter01.step02;

/*
step02 new: refactor
소극장이 셀러의 사무실에서 티켓을 get 하고 있고, 관객의 가방에 티켓을 넣고, 돈을 차감하고 있다.
티켓이라는 데이터는 셀러의 책임이고, 관객의 가방,돈은 관객의 책임이다.
소극장은 셀러에게 "판매해"라는 메세지를, 셀러는 관객에게 "구매해"라는 메세지를 전달한다.
 */
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }
}
