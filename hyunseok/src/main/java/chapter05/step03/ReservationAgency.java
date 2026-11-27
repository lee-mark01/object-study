package chapter05.step03;

// 데이터 클래스들을 조합해서 영화 얘매 절차를 구현한다
public class ReservationAgency {
    // 예매하는 메서드가 필요

    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        return screening.reserve(customer, audienceCount);
    }
}
