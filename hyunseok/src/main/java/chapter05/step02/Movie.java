package chapter05.step02;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public abstract class Movie {

    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;

    public Movie(String title, Duration runningTime, Money fee, DiscountCondition... discountConditions) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountConditions = Arrays.asList(discountConditions);
    }

    /**
     * Screening으로 수신한 메시지
     * 알아야 하는 정보 - 영화의 기본 요금, 할인 관련 정보
     * @param screening
     * @return
     */
    public Money calculateMovieFee(Screening screening) {
        if (isDiscountable(screening)) {
            return fee.minus(calculateDiscountAmount());
        }

        return fee;
    }

    private boolean isDiscountable(Screening screening) {
        return discountConditions.stream()
                .anyMatch(condition -> condition.isSatisfiedBy(screening));
    }

    // 할인 여부를 판단하라 -> 판단 후 각각 타입에 대한 할인 금액 계산(추상 메서드 선언)
    abstract protected Money calculateDiscountAmount();

    protected Money getFee() {
        return fee;
    }
}
