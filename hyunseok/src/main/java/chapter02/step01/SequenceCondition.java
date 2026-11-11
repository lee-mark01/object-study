package chapter02.step01;

public class SequenceCondition implements DiscountCondition{
    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    /**
     * 순번 조건 객체의 순번과 상영 객체의 순번의 일치 여부를 반환
     * @param screening
     * @return
     */
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
