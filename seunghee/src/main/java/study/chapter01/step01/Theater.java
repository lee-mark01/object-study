package study.chapter01.step01;
/*
소극장은 판매자를 가진 상태로 생성된다.

관객이 가방에 초대장이 있으면 티켓 만들어서 가방에 넣어주기

관객의 가방에 초대장이 없으면 티켓을 만들어서 관람객의 돈을 줄이고 판매자의 돈을 늘려서 가방에 넣어주기
 */
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller){
        this.ticketSeller = ticketSeller;
    }
    public void enter(Audience audience){
        if (audience.getBag().hasInvitation()){
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketSeller.getTicketOffice().plusAccount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
