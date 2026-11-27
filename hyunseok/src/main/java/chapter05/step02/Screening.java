package chapter05.step02;

import java.time.LocalDateTime;

public class Screening {

    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreend;

    // 책임: 예매하라
    public Reservation reserve(Customer customer, int audienCount) {
        return new Reservation(customer, this, calculateFee(audienCount), audienCount);
    }

    private Money calculateFee(int audienceCount) {
        /**
         * Movie에 전송하는 메시지인 calculateMovieFee의 메서드 시그니처를 Screening screening로 했다
         * 송신자인 Screeing의 의도를 표현했다
         */
        return movie.calculateMovieFee(this).times(audienceCount);
    }

    public Movie getMovie() {
        return movie;
    }

    public int getSequence() {
        return sequence;
    }

    public LocalDateTime getWhenScreend() {
        return whenScreend;
    }
}
