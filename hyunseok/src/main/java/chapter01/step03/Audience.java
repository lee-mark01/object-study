package chapter01.step03;

/**
 * 관람객, 가방을 소지할 수 있음
 */
public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    /**
     * 가방에게 티켓을 넘겨주고 알아서 판단하라고 처리
     * @param ticket
     * @return
     */
    public Long buy(Ticket ticket) {
        return bag.hold(ticket);
    }
}

