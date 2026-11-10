package chapter01.step03;

/**
 * 소극장
 */
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    /**
     * 관람객이 극장 안으로 입장할 때, 판매원이 판매 행위를 자율적으로 진행
     * 매표소에 접근하는 코드는 사라짐
     * @param audience
     */
    public void enter(Audience audience) {
        ticketSeller.sellTo(audience);
    }
}
