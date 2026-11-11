package study.chapter02.step03

import java.time.LocalDateTime

class Screening(
    private val movie: Movie,
    private val sequence: Int,
    private val whenScreened: LocalDateTime
) {
    fun getStartTime(): LocalDateTime = whenScreened

    fun isSequence(sequence: Int): Boolean = this.sequence == sequence

    fun getMovieFee(): Money = movie.fee

    fun reserve(customer: Customer, audienceCount: Int): Reservation =
        Reservation(customer, this, calculateFee(audienceCount), audienceCount)

    // 요금 계산은 Movie에게 메시지로 요청할 뿐, Movie가 내부에서 어떻게 계산하는지는 모른다.
    private fun calculateFee(audienceCount: Int): Money =
        movie.calculateMovieFee(this).times(audienceCount.toDouble())
}
