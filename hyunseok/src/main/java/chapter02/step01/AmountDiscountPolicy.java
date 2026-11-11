package chapter02.step01;

public class AmountDiscountPolicy extends DiscountPolicy{
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