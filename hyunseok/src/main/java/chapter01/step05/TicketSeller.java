package chapter01.step05;

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
     * 관람객한테 티켓을 판매한다는 행위를 매표소에 넘김
     * @param audience
     */
    public void sellTo(Audience audience) {
            ticketOffice.sellTicketTo(audience);
    }
}
