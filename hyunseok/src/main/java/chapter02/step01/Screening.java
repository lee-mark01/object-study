package chapter02.step01;


import java.time.LocalDateTime;

/**
 * 상영 클래스
 */
public class Screening {
    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    public Money getMovieFee() {
        return movie.getFee();
    }

    /**
     * 상영하는 영화를 예매하는 메서드, 예매 정보를 만들어 반환
     * @param customer 예매자
     * @param audienceCount 예매 인원
     * @return 예약 정보
     */
    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    /**
     * 전체 영화 요금을 계산하는 메서드
     * @param audienceCount 예매 인원
     * @return 예매 요금
     */
    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}
