package study.chapter01.step01;
/*
관람객은 가방을 가진 상태로 생성된다.
관람객은 가진 가방을 반환할 수 있다.
 */
public class Audience {
    private Bag bag;

    public Audience(Bag bag){
        this.bag = bag;
    }
    public Bag getBag(){
        return bag;
    }
}
