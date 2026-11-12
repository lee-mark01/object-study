package study.chapter01.step02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
매표소는 여러 티켓과 현금을 보관하는 상태로 생성된다
티켓 한장을 꺼내줄 수 있다.
매표소 현금을 늘리거나 줄일 수 있다.
 */
public class TicketOffice {
    private long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice (long amount, Ticket... tickets){
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public Ticket getTicket(){
        return tickets.remove(0);
    }

    public void minusAmount(long amount){
        this.amount -= amount;
    }

    public void plusAmount(long amount){
        this.amount += amount;
    }
}
