package chapter01.step02;

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
     * 티켓을 판매하는 메서드(편의상 티켓 컬렉션의 맨 앞을 반환)
     * @return
     */
    public Ticket getTicket() {
        return tickets.remove(0);
    }

    /**
     * 판매금액을 차감하거나 더하는 메서드
     * @param amount
     */
    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
