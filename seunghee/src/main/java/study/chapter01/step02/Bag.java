package study.chapter01.step02;
/*
가방은 초대장 없이 현금만 보유하거나,
초대장과 현금을 함께 보관하는 상태로 생성된다.

초대장과 티켓의 보유 여부를 확인할 수 있다.
티켓을 가방에 넣을 수 있다.
현금을 늘리거나 줄일 수 있다.
 */

public class Bag {
    private long amount;
    private Invitation invitation;
    private Ticket ticket;

    public Bag(long amount){
        this(null, amount);
    }

    public Bag(Invitation invitation, long amount){
        this.invitation = invitation;
        this.amount = amount;
    }

    public boolean hasInvitation(){
        return invitation != null;
    }

    public boolean hasTicket(){
        return ticket != null;
    }

    public void setTicket(Ticket ticket){
        this.ticket = ticket;
    }

    public void plusAmount(long amount){
        this.amount += amount;
    }

    public void minusAmount(long amount){
        this.amount -= amount;
    }
}
