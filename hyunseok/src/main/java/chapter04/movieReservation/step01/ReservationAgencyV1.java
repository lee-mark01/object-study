package chapter04.movieReservation.step01;


// 데이터 클래스들을 조합해서 영화 얘매 절차를 구현한다
public class ReservationAgencyV1 {

    // 할인 정책과 할인 금액을 정하는 메서드
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        boolean discountable = checkDiscountable(screening);
        Money fee = calculateFee(screening, discountable, audienceCount);
        return createReservation(screening, customer, audienceCount, fee);
    }

    private boolean checkDiscountable(Screening screening) {
        return screening.getMovie().getDiscountConditions().stream()
                .anyMatch(condition -> isDiscountable(condition, screening));
    }

    private boolean isDiscountable(DiscountCondition condition, Screening screening) {
        if(condition.getType() == DiscountConditionType.PERIOD) {
            return isSatisfiedByPeriod(condition, screening);
        }

        return isSaticfiedBySequence(condition, screening);
    }

    private boolean isSatisfiedByPeriod(DiscountCondition condition, Screening screening) {
        return screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                condition.getStartTime().compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
    }

    private boolean isSaticfiedBySequence(DiscountCondition condition, Screening screening) {
        return condition.getSequence() == screening.getSequence();
    }

    private Money calculateFee(Screening screening, boolean discountable, int audienceCount) {
        if(discountable) {
            return screening.getMovie().getFee()
                    .minus(calculateDiscontedFee(screening.getMovie()))
                    .times(audienceCount);
        }

        return screening.getMovie().getFee().times(audienceCount);
    }

    private Money calculateDiscontedFee(Movie movie) {
        switch (movie.getMovieType()) {
            case AMOUNT_DISCOUNT -> calculateDiscontedFee(movie);
            case PERCENT_DISCOUNT -> calculateDiscontedFee(movie);
            case NONE_DISCOUNT -> calculateDiscontedFee(movie);
        }

        throw new IllegalArgumentException();
    }

    private Money calculateAmountDiscountFee(Movie movie) {
        return movie.getDiscountAmount();
    }

    private Money calculatePercentDiscountFee(Movie movie) {
        return movie.getFee().times(movie.getDiscountPercent());
    }

    private Money calculateNoneDiscountFee(Movie movie) {
        return Money.ZERO;
    }

    private Reservation createReservation(Screening screening, Customer customer, int audienceCount, Money fee) {
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
