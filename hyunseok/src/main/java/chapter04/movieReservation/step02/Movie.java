package chapter04.movieReservation.step02;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

// 필요한 오퍼레이션은 영화 요금 계산과 할인 여부를 판단이 필요하다
public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;

    private MovieType movieType; // 할인 정책 타입을 정한다.
    private Money discountAmount; // 금액 할인일 경우 할인되는 금액
    private double discountPercent; // 비율 할인일 경우 할인되는 비율

    // 내부에 데이터를 반환하는 접근자와 데이터를 변경하는 수정자 추가
    public MovieType getMovieType() {
        return movieType;
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateAmountDiscountedFee() {
        if(movieType != MovieType.AMOUNT_DISCOUNT) {
            throw new IllegalArgumentException();
        }

        return fee.minus(discountAmount);
    }

    // 할인 정책 타입을 판단하는 메서드
    public boolean isDiscountable(LocalDateTime whenScreened, int sequence) {
        for (DiscountCondition condition : discountConditions) {
            if(condition.getType() == DiscountConditionType.PERIOD) {
                if(condition.isDiscountable(whenScreened.getDayOfWeek(), whenScreened.toLocalTime())) {
                    return true;
                }
            } else {
                if(condition.isDiscountable(sequence)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 할인 정책을 판단해서 적용하는 3가지 메서드
    public Money calculatePercentDiscountedFee() {
        if(movieType != MovieType.PERCENT_DISCOUNT) {
            throw new IllegalArgumentException();
        }

        return fee.minus(fee.times(discountPercent));
    }

    public Money calculateNoneDiscountedFee() {
        if(movieType != MovieType.NONE_DISCOUNT) {
            throw new IllegalArgumentException();
        }

        return fee;
    }
}
