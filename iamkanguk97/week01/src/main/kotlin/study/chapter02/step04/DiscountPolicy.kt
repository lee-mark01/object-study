package study.chapter02.step04

// 할인 정책이 지켜야 할 약속(메시지)만 정의한다.
// 조건 검사 같은 구현은 DefaultDiscountPolicy로 옮겨, 그 구현이 필요 없는 정책은 약속만 지키면 되게 한다.
interface DiscountPolicy {
    fun calculateDiscountAmount(screening: Screening): Money
}
