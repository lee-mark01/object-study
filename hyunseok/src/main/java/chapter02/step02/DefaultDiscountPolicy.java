package chapter02.step02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultDiscountPolicy implements DiscountPolicy{
    private List<DiscountCondition> conditions = new ArrayList<>();

    /**
     * 여러 개의 할인 조건을 받아 리스트 형태로 저장
     * @param conditions
     */
    public DefaultDiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    /**
     * 하나의 할인 정책은 여러 개의 할인 조건을 포함할 수 있음
     * 할인 조건 일치 여부에 따라 true or false 반환
     * @param screening
     * @return
     */
    public Money calculateDiscountAmount(Screening screening) {
        for(DiscountCondition each : conditions) {
            if(each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening screening);
}
