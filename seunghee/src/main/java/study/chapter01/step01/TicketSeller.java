package study.chapter01.step01;
/*
판매자는 티켓 매표소와 함께하는 상태로 생성된다.
매표소를 반환할 수 있다.
 */
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice){
        this.ticketOffice = ticketOffice;
    }

    public TicketOffice getTicketOffice(){
        return ticketOffice;
    }
}
