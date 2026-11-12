package chapter02.step02;

import java.time.Duration;

/**
 * 영화
 */
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    /**
     * 할인 정책을 확인 후 금액을 반환한다.
     * @param screening
     * @return
     */
    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }

    /**
     * 이 방법이 왜 특별하다고 불리는 것일까?
     * 본문 중 상속보다 인스턴스 변수로 관계를 연결한 원래의 설계가 더 유연하다에서 나온 고민임
     * @param discountPolicy
     */
    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
