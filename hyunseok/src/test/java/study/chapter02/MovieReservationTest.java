package study.chapter02;


import chapter02.step02.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("영화 예매")
public class MovieReservationTest {

    private static final Money MOVIE_FEE = Money.wons(10_000L);
    private static final LocalDateTime MONDAY_11 = LocalDateTime.of(2026, 11, 16, 11, 0);
    private static final LocalDateTime TUESDAY_11 = LocalDateTime.of(2026, 11, 17, 11, 0);

    @Nested
    @DisplayName("1회차 순번 조건은")
    class SequenceConditionTest {

        private final DiscountCondition condition = new SequenceCondition(1);

        @Test
        @DisplayName("1회차 상영이면 만족한다")
        void satisfiedByFirstSequence() {
            // 1회차 상영
            Screening screening = screening(movie(new NoneDiscountPolicy()), 1, MONDAY_11);

            // 조건 확인
            boolean satisfied = condition.isSatisfiedBy(screening);

            // 1번이니 만족
            assertTrue(satisfied);
        }

        @Test
        @DisplayName("2회차 상영이면 만족하지 않는다")
        void notSatisfiedBySecondSequence() {
            Screening screening = screening(movie(new NoneDiscountPolicy()), 2, MONDAY_11);

            assertFalse(condition.isSatisfiedBy(screening));
        }



    }

    @Nested
    @DisplayName("월요일 10시~12시 기간 조건은")
    class  PeriodConditionTest {

        private final DiscountCondition condition = new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0));

        @Test
        @DisplayName("월요일 11시 상영이면 만족한다")
        void satisfiedInsidePeriod() {

            assertTrue(condition.isSatisfiedBy(screeningAt(MONDAY_11)));
        }

        @Test
        @DisplayName("월요일 10시 정각과 12시 정각 상영도 만족한다")
        void satisfiedAtBoundary() {
            LocalDateTime start = LocalDateTime.of(2026, 11, 16, 10, 0);
            LocalDateTime end = LocalDateTime.of(2026, 11, 16, 12, 0);

            assertTrue(condition.isSatisfiedBy(screeningAt(start)));
            assertTrue(condition.isSatisfiedBy(screeningAt(end)));
        }

        @Test
        @DisplayName("월요일 13시 상영이면 만족하지 않는다")
        void notSatisfiedOutsideTime() {
            LocalDateTime afternoon = LocalDateTime.of(2026, 11, 16, 13, 0);

            assertFalse(condition.isSatisfiedBy(screeningAt(afternoon)));
        }

        @Test
        @DisplayName("화요일 11시 상영이면 만족하지 않는다")
        void notSatisfiedOnOtherDay() {
            assertFalse(condition.isSatisfiedBy(screeningAt(TUESDAY_11)));
        }

        private Screening screeningAt(LocalDateTime when) {
            return screening(movie(new NoneDiscountPolicy()), 1, when);
        }
    }

    @Nested
    @DisplayName("1회차에 800원을 깎아 주는 금액 할인 정책은")
    class AmountDiscountPolicyTest {

        private final DiscountPolicy policy = new AmountDiscountPolicy(Money.wons(800L), new SequenceCondition(1));

        @Test
        @DisplayName("1회차 상영이면 800원을 할인한다")
        void discountWhenSatisfied() {
            // 해당 정책을 쓰는 영화의 1회차 상영
            Screening screening = screening(movie(policy), 1, MONDAY_11);

            // 할인 금액을 계산
            Money discount = policy.calculateDiscountAmount(screening);

            // 800원
            assertEquals(Money.wons(800L), discount);
        }

        @Test
        @DisplayName("2회차 상영이면 할인하지 않는다")
        void noDiscountWhenNotSatisfied() {
            Screening screening = screening(movie(policy), 2, MONDAY_11);

            assertEquals(Money.ZERO, policy.calculateDiscountAmount(screening));
        }

        @Test
        @DisplayName("조건이 여러 개면 하나만 맞아도 할인한다")
        void discountWhenAnyConditionSatisfied() {
            // 1회차 또는 월요일 10시~12시 조건
            DiscountPolicy orPolicy = new AmountDiscountPolicy(Money.wons(800L),
                    new SequenceCondition(1),
                    new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)));

            // 2회차라 순번 조건은 틀리고, 월요일 11시라 기간 조건만 해당
            Screening screening = screening(movie(orPolicy), 2, MONDAY_11);

            assertEquals(Money.wons(800L), orPolicy.calculateDiscountAmount(screening));
        }
    }

    @Nested
    @DisplayName("1회차에 10%를 깎아 주는 비율 할인 정책은")
    class PercentDiscountPolicyTest {

        @Test
        @DisplayName("1회차 상영이면 1,000원을 할인한다")
        void discountTenPercent() {
            DiscountPolicy policy = new PercentDiscountPolicy(0.1, new SequenceCondition(1));
            Screening screening = screening(movie(policy), 1, MONDAY_11);

            // 소수점까지 계산이라 Money.equals를 compareTo로 바꿈
            assertEquals(Money.wons(1_000L), policy.calculateDiscountAmount(screening));
        }
    }

    @Nested
    @DisplayName("할인 없음 정책은")
    class NoneDiscountPolicyTest {

        @Test
        @DisplayName("어떤 상영이든 할인하지 않는다")
        void neverDiscount() {
            DiscountPolicy policy = new NoneDiscountPolicy();

            assertEquals(Money.ZERO, policy.calculateDiscountAmount(screening(movie(policy), 1, MONDAY_11)));
            assertEquals(Money.ZERO, policy.calculateDiscountAmount(screening(movie(policy), 2, TUESDAY_11)));
        }
    }

    @Nested
    @DisplayName("800원 금액 할인 영화는")
    class MovieTest {

        private final Movie movie =
                movie(new AmountDiscountPolicy(Money.wons(800L), new SequenceCondition(1)));

        @Test
        @DisplayName("조건에 맞는 상영이면 요금이 9,200원이다")
        void feeWithAmountDiscount() {
            Screening screening = screening(movie, 1, MONDAY_11);

            assertEquals(Money.wons(9_200L), movie.calculateMovieFee(screening));
        }

        @Test
        @DisplayName("정책을 10% 할인으로 바꾸면 요금이 9,000원이다")
        void feeAfterChangingPolicy() {
            // When: 실행 중에 정책을 바꾼다 (합성이라 가능)
            movie.changeDiscountPolicy(new PercentDiscountPolicy(0.1, new SequenceCondition(1)));

            Screening screening = screening(movie, 1, MONDAY_11);

            assertEquals(Money.wons(9_000L), movie.calculateMovieFee(screening));
        }
    }

    @Nested
    @DisplayName("상영을 예매할 때")
    class ReserveTest {

        @Test
        @DisplayName("800원 할인 1회차를 2명이 예매하면 18,400원이다")
        void reserveWithDiscount() {
            Movie movie = movie(new AmountDiscountPolicy(Money.wons(800L), new SequenceCondition(1)));
            Screening screening = screening(movie, 1, MONDAY_11);

            Reservation reservation = screening.reserve(new Customer(), 2);

            assertEquals(Money.wons(18_400L), reservation.paid());
        }

        @Test
        @DisplayName("할인 없는 영화를 3명이 예매하면 30,000원이다")
        void reserveWithoutDiscount() {
            Screening screening = screening(movie(new NoneDiscountPolicy()), 1, MONDAY_11);

            Reservation reservation = screening.reserve(new Customer(), 3);

            assertEquals(Money.wons(30_000L), reservation.paid());
        }
    }

    private Movie movie(DiscountPolicy policy) {
        return new Movie("아바타", Duration.ofMinutes(120), MOVIE_FEE, policy);
    }

    private Screening screening(Movie movie, int sequence, LocalDateTime when) {
        return new Screening(movie, sequence, when);
    }
}
