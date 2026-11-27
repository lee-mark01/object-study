package chapter05.step03;

public class AmountDiscountMovie extends DiscountPolicy {

    private Money discountAmount;

    public AmountDiscountMovie(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }
}
