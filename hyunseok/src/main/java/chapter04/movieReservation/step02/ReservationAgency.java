package chapter04.movieReservation.step02;


// 데이터 클래스들을 조합해서 영화 얘매 절차를 구현한다
public class ReservationAgency {

    // 할인 정책과 할인 금액을 정하는 메서드
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        Money fee = screening.calculateFee(audienceCount);
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
