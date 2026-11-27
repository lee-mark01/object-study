package chapter05.step03;

public class PercentDiscountMovie extends DiscountPolicy {

    private double percent;

    public PercentDiscountMovie(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovie().getFee().times(percent);
    }
}
