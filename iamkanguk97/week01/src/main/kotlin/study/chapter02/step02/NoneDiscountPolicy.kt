package study.chapter02.step02

// "할인 정책 없음"도 하나의 할인 정책으로 본다.
// 덕분에 Movie는 정책 유무를 따지지 않고 언제나 DiscountPolicy에 할인 금액을 묻기만 하면 된다.
class NoneDiscountPolicy : DiscountPolicy() {
    override fun getDiscountAmount(screening: Screening): Money = Money.ZERO
}
