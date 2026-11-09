package chapter01.step01;

/**
 * 티켓 판매원
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

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }
}
