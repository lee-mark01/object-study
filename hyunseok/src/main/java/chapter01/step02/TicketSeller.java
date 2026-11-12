package chapter01.step02;

/**
 * 티켓 판매원
 * 매표소에 관한 접근 권한을 오직 판매원만 가지게 됨
 * 티켓을 꺼내거나 판매하는 행위를 판매원만 가능하게 됨(캡슐화)
 */
public class TicketSeller {
    private TicketOffice ticketOffice;

    /**
     * 자신이 근무하는 매표소를 알아야 함
     * @param ticketOffice
     */
    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    /**
     * 관람객한테 산다는 행동만 호출하게 한 뒤 티켓을 줌
     * @param audience
     */
    public void sellTo(Audience audience) {
            ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }
}
