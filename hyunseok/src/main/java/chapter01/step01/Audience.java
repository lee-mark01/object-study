package chapter01.step01;

/**
 * 관람객, 가방을 소지할 수 있음
 */
public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }
}
