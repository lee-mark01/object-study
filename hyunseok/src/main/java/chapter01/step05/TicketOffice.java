package chapter01.step05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 매표소
 */
public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    /**
     *  티켓의 판매 금액과 판매할 티켓들의 목록을 가진다
     * @param amount
     * @param tickets
     */
    public TicketOffice(Long amount, Ticket... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    /**
     * 관람객한테 티켓을 판매함
     * 1. 돈을 받고
     * 2. 티켓을 판다
     * 3. 잔액이 부족하면 잔액 부족 메시지 반환됨
     * @param audience
     */
    public void sellTicketTo(Audience audience) {
        Long paid = audience.buy(tickets.get(0));
        tickets.remove(0);
        plusAmount(paid);
    }

    /**
     * 티켓을 판매하는 메서드(편의상 티켓 컬렉션의 맨 앞을 반환)
     * @return
     */
    private Ticket getTicket() {
        return tickets.remove(0);
    }

    /**
     * 판매금액을 더하는 메서드
     * @param amount
     */
    private void plusAmount(Long amount) {
        this.amount += amount;
    }
}
