package chapter02.step02;

public class AmountDiscountPolicy extends DefaultDiscountPolicy {
    private Money discountMoney;

    public AmountDiscountPolicy(Money discountMoney, DiscountCondition... conditions) {
        super(conditions);
        this.discountMoney = discountMoney;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountMoney;
    }
}