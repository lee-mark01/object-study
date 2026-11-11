package study.chapter02.step03

// 인터페이스를 직접 구현해, 쓰지도 않는 조건 검사 로직을 물려받지 않는다.
class NoneDiscountPolicy : DiscountPolicy {
    override fun calculateDiscountAmount(screening: Screening): Money = Money.ZERO
}
