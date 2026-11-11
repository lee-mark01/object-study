# 객체지향 프로그래밍

## 영화 예매 시스템

### 영화와 상영이라는 단어의 구분

- 영화: 영화에 대한 기본 정보
  - 제목, 상영시간, 가격정보
  - 위와 같이 영화가 가지고 있는 기본적인 정보를 가리키는 것

- 상영: 실제로 관객들이 영화를 관람하는 사건
  - 상영일자, 시간, 순번
  - 영화는 하루 중 다양한 시간대에 걸쳐 한 번 이상 상영될 수 있다.

사용자가 실제로 예매하는 대상은 영화가 아니라 상영이다.
소비자는 특정 시간에 상영되는 영화를 관람하기 위해 돈을 지불한다.

### 할인 조건 및 정책

- 할인 조건: 가격의 할인 여부를 결정하고 크게 `순서조건`과 `기간조건` 으로 나눌 수 있다.
  - 순서조건: 상영 순번을 이용해 할인 여부를 결정
    - ex. 순서조건의 순번이 10인 경우 매일 10번째로 상영되는 영화를 예매한 사용자들에게 할인 혜택 제공
  - 기간조건: 영화 상영 시작 시간을 이용해 할인 여부 결정
    - 요일, 시작시간, 종료시간의 세 부분으로 구성되고 영화 시작 시간이 해당 기간안에 포함될 경우 요금 할인
    - ex. 요일이 월요일 / 시작시간이 오전10시 / 종료시간이 오후1시인 기간조건 -> 매주 월요일 오전 10시부터 오후 1시 사이에 상영되는 모든 영화 할인
  - 영화에 여러 개의 할인 조건을 적용할 수 있다. 섞는 것도 가능함. 조건들 중 하나라도 만족하면 할인이 적용됨.

- 할인 정책: 할인 요금 결정하고 크게 `금액할인정책`과 `비율할인정책`이 있다.
  - 금액할인정책: 예매 요금에서 일정 금액을 할인해주는 방식
  - 비율할인정책: 정가에서 일정 비율의 요금을 할인해주는 방식
  - 영화별로 하나의 할인 정책만 할당할 수 있다. (할인정책 적용하지 않는것도 가능)

## 객체지향 프로그래밍을 향해

클래스가 아닌 객체에 초점을 맞춰야 한다.
- 어떤 클래스가 필요한지 고민하기 전에 어떤 객체들이 필요한지 고민하자.
  - `클래스`는 **공통적인 상태와 행동을 공유하는 객체들을 추상화한 것**
- 객체를 독립적인 존재가 아니라 기능을 구현하기 위해 협력하는 공동체의 일원으로 보자.
  - 이 습관은 설계를 유연하고 확장 가능하게 만든다.

### 도메인의 구조를 따르는 프로그램 구조

`도메인`: 문제를 해결하기 위해 사용자가 프로그램을 사용하는 분야
일반적으로 클래스의 이름은 대응되는 도메인 개념의 이름과동일하거나 적어도 유사하게 지어야 한다.

- Reservation
- Screening
- Movie
- DiscountPolicy -> AmountDiscountPolicy + PercentDiscountPolicy
- DiscountCondition -> SequenceCondition + PeriodCondition

클래스의 내부와 외부를 구분해야 하는 이유?
-> 경계의 명확성이 객체의 자율성을 보장하기 때문.
-> 프로그래머에게 구현의 자유를 제공하기 때문.

### 자율적인 객체

객체는 `상태(state)`와 `행동(behavior)`을 함께 가지는 복합적인 존재이다.
그리고 객체는 **스스로 판단하고 행동하는 자율적인 존재**이다.

`캡슐화`: 데이터와 기능을 객체 내부로 함께 묶는 것

대부분의 객체지향 프로그래밍 언어들은 캡슐화하는 것에서 나아가서 외부에서의 접근을 통제할 수 있는 접근 제어 메커니즘도 제공한다.
`public, protected, private`과 같은 접근 수정자를 제공한다.
이렇게 접근을 통제하는 이유는 객체를 자율적인 존재로 만들기 위해서이다. (외부에서의 간섭을 최소화)

외부에서 접근 가능한 부분: 퍼블릭 인터페이스
내부에서만 접근 가능한 부분: 구현

객체의 상태는 숨기고 행동만 외부에 공개해야 한다.
(속성은 private, 공개하는 일부 메서드만 public)

사용자에 대한 영향을 걱정하지 않고도 내부 구현을 마음대로 변경할 수 있는 것을 `구현 은닉(Inplementation Hiding)` 이라고 한다.

### 협력에 관한 짧은 이야기

객체가 다른 객체와 상호작용할 수 있는 방법은 `메시지를 전송`하는 것뿐이다.
그리고 수신된 메시지를 처리하기 위한 해당 객체만의 방법을 `메서드` 라고 한다.

Screening 클래스에서 movie.calculateMovieFee
-> "Screening이 Movie의 calculateMovieFee 메서드를 호출한다" (X)
-> "Screening이 Movie에게 calculateMovieFee 메시지를 전송한다" (O)

참고로 Screening은 Movie 안에 calculateMovieFee 메서드가 존재하는지 모른다.
단지 Movie가 calculateMovieFee 메시지에 응답할 수 있다고 믿고 메시지를 전송한다.

### 템플릿 메서드 패턴

부모 클래스에 기본적인 알고리즘 흐름을 구현하고 중간에 필요한 처리를 자식 클래스에게 위임하는 디자인 패턴

영화 예매 시스템의 할인 정책이 이 구조이다.
- 공통 흐름: "할인 조건 중 하나라도 만족하면 할인 금액을 계산하고, 하나도 만족하지 않으면 0원" → 금액할인이든 비율할인이든 같다.
- 달라지는 부분: "할인 금액을 얼마로 계산하는가" → 정책마다 다르다.

```kotlin
abstract class DiscountPolicy(vararg conditions: DiscountCondition) {
    private val conditions: List<DiscountCondition> = conditions.toList()

    // 템플릿 메서드: 전체 흐름은 부모가 정한다.
    fun calculateDiscountAmount(screening: Screening): Money {
        for (condition in conditions) {
            if (condition.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening) // 달라지는 부분만 자식에게 맡긴다.
            }
        }
        return Money.ZERO
    }

    protected abstract fun getDiscountAmount(screening: Screening): Money
}

class AmountDiscountPolicy(...) : DiscountPolicy(*conditions) {
    override fun getDiscountAmount(screening: Screening): Money = discountAmount
}

class PercentDiscountPolicy(...) : DiscountPolicy(*conditions) {
    override fun getDiscountAmount(screening: Screening): Money = screening.getMovieFee().times(percent)
}
```

- `calculateDiscountAmount`: 흐름을 정의하는 **템플릿 메서드**. 자식은 이 흐름을 바꿀 수 없다.
  - 코틀린은 메서드가 기본적으로 `final`이라 `open`을 붙이지 않는 한 자식이 재정의할 수 없다. 흐름이 자연스럽게 고정된다.
- `getDiscountAmount`: 자식이 채워 넣는 빈칸. `abstract`라서 자식은 반드시 구현해야 한다.
  - `protected`인 이유: 외부에서 직접 부를 메서드가 아니라 템플릿 메서드 안에서만 쓰이는 내부 단계이기 때문이다.

얻는 것
- 조건 검사 로직이 부모 한 곳에만 있다. 정책마다 같은 `for` 문을 복사하지 않아도 된다.
- 새로운 할인 정책을 추가할 때 `getDiscountAmount`만 구현하면 된다.
- `Movie`는 `DiscountPolicy`에게 `calculateDiscountAmount` 메시지만 보낸다. 실제로 어떤 정책인지 몰라도 된다.

### 오버라이딩과 오버로딩

이름이 비슷하지만 전혀 다른 개념이다.

- `오버라이딩(Overriding)`: 부모 클래스에 있는 메서드와 **이름과 파라미터 목록이 같은** 메서드를 자식 클래스에서 다시 정의하는 것
  - 자식의 메서드가 부모의 메서드를 가린다. 부모 타입으로 메시지를 보내도 실제 객체(자식)의 메서드가 실행된다.
  - 어떤 메서드가 실행될지는 **실행 시점**에 실제 객체를 보고 정해진다. (동적 바인딩)
  - 코틀린에서는 부모 쪽에 `open` 또는 `abstract`, 자식 쪽에 `override` 키워드가 필요하다.

- `오버로딩(Overloading)`: 한 클래스 안에서 **이름은 같지만 파라미터 목록이 다른** 메서드를 여러 개 두는 것
  - 기존 메서드를 가리지 않고 나란히 공존한다.
  - 어떤 메서드가 호출될지는 **컴파일 시점**에 넘긴 인자의 타입을 보고 정해진다.
  - 반환 타입만 다른 것은 오버로딩이 아니다. (컴파일러가 구분할 수 없어서 컴파일 에러)

```kotlin
// 오버라이딩: 부모의 getDiscountAmount를 자식이 다시 정의
class AmountDiscountPolicy(...) : DiscountPolicy(*conditions) {
    override fun getDiscountAmount(screening: Screening): Money = discountAmount
}

// 오버로딩: 이름(wons)은 같고 파라미터 타입(Long / Double)만 다름
companion object {
    fun wons(amount: Long): Money = Money(BigDecimal.valueOf(amount))
    fun wons(amount: Double): Money = Money(BigDecimal.valueOf(amount))
}
```

| 구분 | 오버라이딩 | 오버로딩 |
|---|---|---|
| 위치 | 부모 ↔ 자식 클래스 사이 | 같은 클래스 안 |
| 메서드 이름 | 같음 | 같음 |
| 파라미터 목록 | 같음 | 다름 |
| 기존 메서드와 관계 | 가린다(대체) | 공존한다(추가) |
| 결정 시점 | 실행 시점 (실제 객체 기준) | 컴파일 시점 (인자 타입 기준) |

템플릿 메서드 패턴은 오버라이딩 덕분에 동작한다.
부모의 `calculateDiscountAmount`가 `getDiscountAmount`를 부르면, 실행 시점에 실제 객체가 `AmountDiscountPolicy`인지 `PercentDiscountPolicy`인지에 따라 자식이 오버라이딩한 메서드가 실행된다.
