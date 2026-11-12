package chapter01.step04;

// 공연을 관람하기 위해 모든 사람이 소지해야 하는 티켓
public class Ticket {
    private Long fee;

    // 티켓의 값을 정해주기 위해 생성자를 추가
    public Ticket(Long fee) {
        this.fee = fee;
    }

    public Long getFee() {
        return fee;
    }
}
