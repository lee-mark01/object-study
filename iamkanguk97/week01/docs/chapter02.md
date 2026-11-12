# 2장: 객체지향 프로그래밍

## 1. 영화 예매 시스템

### 영화와 상영

| 용어 | 의미 | 가진 정보 |
|---|---|---|
| **영화** | 영화에 대한 기본 정보 | 제목, 상영 시간, 가격 |
| **상영** | 관객이 실제로 영화를 관람하는 사건 | 상영 일자, 시간, 순번 |

영화 하나는 하루에도 여러 시간대에 걸쳐 한 번 이상 상영될 수 있다.
사용자가 실제로 예매하는 대상은 영화가 아니라 **상영**이다. 관객은 특정 시간에 상영되는 영화를 보려고 돈을 낸다.

### 할인 조건과 할인 정책

요금 할인은 두 가지 규칙으로 정해진다.

**할인 조건(discount condition)**: 할인 여부를 결정한다.

| 종류 | 판단 기준 | 예시 |
|---|---|---|
| 순서 조건 | 상영 순번 | 순번이 10이면, 매일 10번째로 상영되는 영화를 예매한 관객에게 할인 |
| 기간 조건 | 상영 시작 시간 (요일, 시작 시간, 종료 시간) | 월요일 10:00~13:00이면, 매주 월요일 그 시간대에 시작하는 상영을 할인 |

- 한 영화에 할인 조건을 여러 개 붙일 수 있고 순서 조건과 기간 조건을 섞어도 된다.
- 조건 중 **하나라도 만족하면** 할인이 적용된다.

**할인 정책(discount policy)**: 할인 금액을 결정한다.

| 종류 | 방식 |
|---|---|
| 금액 할인 정책 | 예매 요금에서 일정 금액을 뺀다 (예: 800원) |
| 비율 할인 정책 | 정가에서 일정 비율을 뺀다 (예: 10%) |

- 영화 하나에는 할인 정책을 **최대 하나만** 지정할 수 있다. 할인 정책을 지정하지 않아도 된다.
- 할인은 관객 한 명의 요금에 먼저 적용하고 그 금액에 인원수를 곱한다.

예제 코드(`Main.kt`)에 등장하는 영화는 다음과 같다.

| 영화 | 정가 | 할인 정책 | 할인 조건 |
|---|---|---|---|
| 아바타 | 10,000원 | 800원 금액 할인 | 1회차, 10회차, 월 10:00~11:59, 목 10:00~20:59 |
| 타이타닉 | 11,000원 | 10% 비율 할인 | 화 14:00~16:59, 2회차, 목 10:00~13:59 |
| 스타워즈 | 10,000원 | 없음 | 없음 |

## 2. 객체지향 프로그래밍을 향해

### 클래스가 아니라 객체에 초점을 맞춘다

- 어떤 클래스가 필요한지 고민하기 전에 **어떤 객체들이 필요한지**부터 고민한다. **클래스**는 공통적인 상태와 행동을 공유하는 객체들을 추상화한 것이기 때문이다.
- 객체를 독립적인 존재로 떼어 놓고 보지 않는다. 기능을 구현하려고 **협력하는 공동체의 일원**으로 본다. 이 습관이 설계를 유연하고 확장 가능하게 만든다.

### 도메인의 구조를 따르는 프로그램 구조

**도메인(domain)**: 문제를 해결하려고 사용자가 프로그램을 사용하는 분야. 여기서는 영화 예매가 도메인이다.

클래스 이름은 대응하는 도메인 개념의 이름과 같거나 적어도 비슷하게 짓는다. 그러면 프로그램 구조가 도메인 구조를 그대로 따라가서 이해하고 예상하기 쉬워진다.

```text
Reservation(예매) ←─생성─ Screening(상영) ──→ Movie(영화)
                                               │ 0..1개
                                               ↓
                                        DiscountPolicy(할인 정책)
                                         ├─ AmountDiscountPolicy
                                         └─ PercentDiscountPolicy
                                               │ N개
                                               ↓
                                        DiscountCondition(할인 조건)
                                         ├─ SequenceCondition
                                         └─ PeriodCondition
```

### 자율적인 객체

객체는 **상태(state)**와 **행동(behavior)**을 함께 가진 존재이고 **스스로 판단하고 행동하는 자율적인 존재**다.

- **캡슐화(encapsulation)**: 데이터와 기능을 객체 내부에 함께 묶는 것.
- **접근 제어(access control)**: 대부분의 객체지향 언어는 캡슐화에서 더 나아가 외부 접근을 통제하는 장치를 제공한다. `public`, `protected`, `private` 같은 **접근 수정자(access modifier)**다. 코틀린은 아무것도 붙이지 않으면 `public`이다.

접근을 통제하는 이유는 객체를 자율적인 존재로 만들기 위해서다. 외부의 간섭을 줄여야 객체가 스스로 판단할 수 있다.

| 구분 | 의미 | 예제에서는 |
|---|---|---|
| **퍼블릭 인터페이스(public interface)** | 외부에서 접근할 수 있는 부분 | `Screening.reserve()`, `Movie.calculateMovieFee()` |
| **구현(implementation)** | 내부에서만 접근할 수 있는 부분 | `private` 필드, `Screening.calculateFee()` |

> 객체의 상태는 숨기고 행동만 외부에 공개한다. 보통 속성은 `private`으로 두고, 외부에 제공할 일부 메서드만 `public`으로 둔다.

**클래스의 내부와 외부를 구분하는 이유**

1. **경계가 분명해야 객체가 자율적일 수 있다.** 외부에서 내부 상태를 마음대로 건드리지 못해야 객체가 자기 상태를 스스로 관리한다.
2. **프로그래머에게 구현의 자유를 준다.** 프로그래머의 역할을 둘로 나눠 보면 이유가 보인다.
   - **클래스 작성자(class creator)**: 새 클래스를 만드는 사람. 클라이언트 프로그래머에게 필요한 부분만 공개하고 나머지는 숨긴다.
   - **클라이언트 프로그래머(client programmer)**: 클래스 작성자가 만든 클래스를 가져다 쓰는 사람. 공개된 인터페이스만 알면 된다.

   숨긴 부분은 클라이언트 프로그래머가 모르므로 클래스 작성자는 클라이언트에 미칠 영향을 걱정하지 않고 내부를 바꿀 수 있다. 이것을 **구현 은닉(implementation hiding)**이라고 한다.

### 협력에 관한 짧은 이야기

객체가 다른 객체와 상호작용하는 방법은 **메시지를 전송하는 것**뿐이다. 객체는 받은 메시지를 자기만의 방법으로 처리한다. 이 방법이 **메서드(method)**다.

`Screening` 안의 `movie.calculateMovieFee(this)`를 두 가지로 읽어 보면 차이가 드러난다.

- "Screening이 Movie의 calculateMovieFee 메서드를 호출한다" (X)
- "Screening이 Movie에게 calculateMovieFee 메시지를 전송한다" (O)

Screening은 Movie 안에 `calculateMovieFee` 메서드가 실제로 있는지 모른다. Movie가 그 메시지에 응답할 수 있다고 믿고 메시지를 보낼 뿐이다. 메시지를 어떻게 처리할지는 받는 객체가 정한다. 메시지와 메서드를 이렇게 구분하는 것이 뒤에서 볼 다형성의 바탕이 된다.

예매 요금을 계산하는 협력은 다음과 같이 흘러간다.

```text
Screening      → Movie             : "1인 요금을 계산해 줘"        calculateMovieFee(screening)
Movie          → DiscountPolicy    : "할인 금액을 계산해 줘"        calculateDiscountAmount(screening)
DiscountPolicy → DiscountCondition : "이 상영이 할인 조건에 맞니?"   isSatisfiedBy(screening)
Screening      → Reservation       : 1인 요금 × 인원수로 예매를 만든다
```

각 객체는 자기가 맡은 일만 하고 나머지는 메시지로 다른 객체에게 요청한다.

### 의미 있는 값은 객체로: Money

금액을 `Long`이나 `BigDecimal` 같은 원시 타입으로 다루면 두 가지 문제가 생긴다.

- 변수에 담긴 값이 **금액이라는 의미**를 전달하지 못한다.
- 더하기, 빼기, 비율 곱하기 같은 금액 계산 로직이 여러 곳에 **중복**된다.

금액은 `Money` 객체로 표현하고 금액 관련 연산(`plus`, `minus`, `times`)은 그 안에 모은다.

> 의미를 더 분명하게 표현할 수 있다면, 인스턴스 변수가 하나뿐인 개념이라도 객체로 만든다. 개념을 명시적으로 드러내는 것이 설계를 명확하고 유연하게 만드는 첫걸음이다.

## 3. 할인 정책과 템플릿 메서드 패턴

### 템플릿 메서드 패턴

**템플릿 메서드 패턴(template method pattern)**: 부모 클래스에 알고리즘의 기본 흐름을 구현하고 중간에 필요한 처리를 자식 클래스에게 맡기는 디자인 패턴.

할인 정책이 이 구조다.

- **공통 흐름**: "할인 조건 중 하나라도 만족하면 할인 금액을 계산하고, 하나도 만족하지 않으면 0원" → 금액 할인이든 비율 할인이든 같다.
- **달라지는 부분**: "할인 금액을 얼마로 계산하는가" → 정책마다 다르다.

```kt
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

- `calculateDiscountAmount`: 흐름을 정하는 **템플릿 메서드**다. 자식은 이 흐름을 바꿀 수 없다.
  - 코틀린은 메서드가 기본적으로 `final`이라서 `open`을 붙이지 않으면 자식이 재정의할 수 없다. 덕분에 흐름이 자연스럽게 고정된다.
  - 단, 이 메서드가 인터페이스 메서드를 `override`하면 상황이 달라진다. step03에서 다룬다(5절).
- `getDiscountAmount`: 자식이 채워 넣는 빈칸이다. `abstract`라서 자식은 반드시 구현해야 한다.
  - `protected`인 이유: 템플릿 메서드 안에서만 쓰는 내부 단계이기 때문이다. 외부에서 직접 부를 메서드가 아니다.

이 구조로 얻는 것은 다음과 같다.

- 조건 검사 로직이 부모 한 곳에만 있다. 정책마다 같은 `for` 문을 복사하지 않아도 된다.
- 새로운 할인 정책을 추가할 때 `getDiscountAmount`만 구현하면 된다.
- Movie는 DiscountPolicy에게 `calculateDiscountAmount` 메시지만 보낸다. 실제로 어떤 정책인지 몰라도 된다.

### 오버라이딩과 오버로딩

이름은 비슷하지만 전혀 다른 개념이다.

- **오버라이딩(overriding)**: 부모 클래스의 메서드와 **이름과 파라미터 목록이 같은** 메서드를 자식 클래스에서 다시 정의하는 것.
  - 자식의 메서드가 부모의 메서드를 가린다. 부모 타입으로 메시지를 보내도 실제 객체(자식)의 메서드가 실행된다.
  - 어떤 메서드가 실행될지는 **실행 시점**에 실제 객체를 보고 정해진다(동적 바인딩).
  - 코틀린에서는 부모 쪽에 `open` 또는 `abstract`, 자식 쪽에 `override` 키워드가 필요하다.
- **오버로딩(overloading)**: 한 클래스 안에 **이름은 같지만 파라미터 목록이 다른** 메서드를 여러 개 두는 것.
  - 기존 메서드를 가리지 않고 나란히 공존한다.
  - 어떤 메서드가 호출될지는 **컴파일 시점**에 넘긴 인자의 타입을 보고 정해진다.
  - 반환 타입만 다른 것은 오버로딩이 아니다. 컴파일러가 둘을 구분할 수 없어서 컴파일 에러가 난다.

```kt
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
부모의 `calculateDiscountAmount`가 `getDiscountAmount`를 부르면 실행 시점에 실제 객체가 `AmountDiscountPolicy`인지 `PercentDiscountPolicy`인지에 따라 자식이 오버라이딩한 메서드가 실행된다.

## 4. 상속과 다형성

Movie 코드 어디에도 할인 정책이 금액 할인인지 비율 할인인지 판단하는 부분이 없는데도 영화마다 다른 방식으로 할인 금액이 계산된다. 상속과 다형성 덕분이다.

### 컴파일 시간 의존성과 실행 시간 의존성

어떤 클래스가 다른 클래스에 접근할 수 있는 경로를 갖거나 그 클래스 객체의 메서드를 호출하면 두 클래스 사이에 **의존성(dependency)**이 있다고 한다.

Movie 코드는 추상 타입인 `DiscountPolicy`에만 의존하지만 실행 중에 Movie 객체가 실제로 협력하는 상대는 `AmountDiscountPolicy`나 `PercentDiscountPolicy` 객체다.

```text
코드(클래스 사이):   Movie ──→ DiscountPolicy

실행(객체 사이):     아바타 Movie 객체   ──→ AmountDiscountPolicy 객체
                    타이타닉 Movie 객체 ──→ PercentDiscountPolicy 객체
```

**코드의 의존성과 실행 시점의 의존성은 다를 수 있다.** 같은 말을 "**클래스 사이의 의존성과 객체 사이의 의존성은 다를 수 있다**"라고도 표현한다.
클래스는 코드에 적힌 구조이고 객체는 실행 중에 실제로 만들어진 것이다. Movie 클래스는 `DiscountPolicy`라는 추상 타입만 알지만 실행 중의 Movie 객체는 각자 생성자로 구체적인 정책 객체를 넘겨받고 그 객체와 연결된다. 이 연결은 Movie 코드 밖, 객체를 만드는 쪽(`Main.kt`)에서 정해진다.

> 코드의 의존성과 실행 시점의 의존성이 다를수록 설계는 유연해지고 확장하기 쉬워진다. 하지만 그만큼 코드를 이해하고 디버깅하기는 어려워진다. Movie 코드만 읽어서는 어떤 할인 정책이 쓰일지 알 수 없기 때문이다.

유연성과 가독성 중 어느 한쪽만 무조건 좇는다고 정답이 되지는 않는다. 둘 사이에서 늘 고민해야 한다.

### 상속

**상속(inheritance)**은 코드를 재사용할 때 널리 쓰는 방법이다. 클래스 사이에 관계를 맺는 것만으로 기존 클래스의 속성과 행동을 새 클래스에 모두 포함시킬 수 있다. DiscountPolicy와 AmountDiscountPolicy/PercentDiscountPolicy가 대표적인 예다.

상속을 쓰면 부모 클래스의 구현은 공유하면서 행동만 다른 자식 클래스를 쉽게 추가할 수 있다. **차이에 의한 프로그래밍(programming by difference)**은 이처럼 부모 클래스와 **다른 부분만 추가해서** 새 클래스를 빠르게 만드는 방법이다.

### 상속과 인터페이스

**인터페이스**는 객체가 이해할 수 있는 메시지의 목록이다. 상속을 받으면 자식 클래스의 인터페이스에 부모 클래스의 인터페이스가 포함된다.

결과적으로 자식 클래스는 부모 클래스가 받을 수 있는 모든 메시지를 받는다. 이 때문에 외부 객체는 자식 클래스를 부모 클래스와 같은 타입으로 취급한다. Movie 입장에서는 상대가 AmountDiscountPolicy든 PercentDiscountPolicy든 `calculateDiscountAmount` 메시지를 이해하기만 하면 된다.

컴파일러도 부모 클래스가 나오는 모든 자리에 자식 클래스를 쓰는 것을 허용한다. **업캐스팅(upcasting)**은 이렇게 자식 클래스가 부모 클래스를 대신하는 것이다. 상속 계층을 그리면 부모가 위에 있으므로 자식을 위쪽 타입으로 취급한다는 뜻에서 붙은 이름이다.

```kt
// 생성자 파라미터 타입은 DiscountPolicy지만, 자식인 AmountDiscountPolicy를 넘길 수 있다.
val avatar = Movie(
    "아바타", Duration.ofMinutes(120), Money.wons(10000),
    AmountDiscountPolicy(Money.wons(800), SequenceCondition(1))
)
```

### 다형성

Movie는 항상 같은 메시지를 보내지만 실제로 어떤 메서드가 실행될지는 메시지를 받는 객체의 클래스에 따라 달라진다. 이것이 **다형성(polymorphism)**이다.

> **다형성**: 같은 메시지를 받았을 때 객체의 타입에 따라 다르게 응답하는 능력.

- 다형성은 컴파일 시간 의존성과 실행 시간 의존성이 다를 수 있다는 사실을 바탕으로 한다. 코드에서는 `Movie → DiscountPolicy`이지만 실행 중에는 `AmountDiscountPolicy`나 `PercentDiscountPolicy`와 협력한다.
- 다형적인 협력에 참여하는 객체들은 모두 같은 메시지를 이해할 수 있어야 한다. 인터페이스가 같아야 한다는 뜻이다.

메시지와 메서드를 실행 시점에 연결하는 **지연 바인딩** 덕분에 다형성이 동작한다. 바로 아래에서 따로 정리한다.

상속이 다형성을 구현하는 유일한 방법은 아니다. 아래에서 볼 인터페이스로도 다형성을 구현할 수 있다.

### 지연 바인딩과 초기 바인딩

**바인딩(binding)**은 메시지(메서드 호출)를 실제로 실행할 메서드와 연결하는 일이다. 둘을 가르는 기준은 **연결 시점**이다.

| 구분 | 다른 이름 | 연결 시점 | 무엇을 보고 정하나 |
|---|---|---|---|
| **지연 바인딩(late binding)** | 동적 바인딩(dynamic binding) | 실행 시점 | 변수가 가리키는 **실제 객체** |
| **초기 바인딩(early binding)** | 정적 바인딩(static binding) | 컴파일 시점 | 변수에 **선언된 타입** |

Movie의 이 줄을 컴파일할 때 컴파일러가 아는 것은 `discountPolicy`가 `DiscountPolicy` 타입이라는 사실뿐이다.

```kt
fee.minus(discountPolicy.calculateDiscountAmount(screening))
```

`javap -c`로 `Movie.class`를 열어 보면 바이트코드에도 인터페이스 이름만 남아 있다. `AmountDiscountPolicy`라는 이름은 어디에도 없다.

```text
invokeinterface  DiscountPolicy.calculateDiscountAmount:(Screening)Money
```

`invokeinterface`는 실행할 때 스택에 올라온 실제 객체의 클래스에서 구현을 찾아 실행하라는 명령이다. 그래서 컴파일된 **한 줄**이 `Main.kt`를 실행하는 동안 서로 다른 메서드를 실행한다.

| 호출 | 그 순간 `discountPolicy`에 든 객체 | 실행되는 메서드 |
|---|---|---|
| 아바타 예매 | `AmountDiscountPolicy` | 금액 할인 |
| 타이타닉 예매 | `PercentDiscountPolicy` | 비율 할인 |
| 스타워즈 예매 | `NoneDiscountPolicy` | 할인 없음 |
| `changeDiscountPolicy` 후 아바타 예매 | `PercentDiscountPolicy` | 비율 할인 |

초기 바인딩이었다면 이 줄은 컴파일할 때 메서드 하나에 고정되어 네 경우 모두 같은 메서드를 실행했을 것이다. 특히 마지막 줄은 같은 아바타 객체, 같은 코드인데도 연결되는 메서드가 바뀐다.

초기 바인딩이 일어나는 대표적인 경우는 다음과 같다.

- **오버로딩 선택**: 이름이 같은 메서드 중 무엇을 부를지 인자의 **선언 타입**으로 정한다(앞의 오버라이딩과 오버로딩 참고).
- **`private` / `final` 메서드, 최상위 함수, `companion object` 함수**: 재정의될 수 없으니 실행할 메서드가 하나로 정해진다.
- **코틀린 확장 함수**: 멤버처럼 보여도 정적으로 결정된다. 가장 착각하기 쉽다.

같은 객체를 대상으로 세 가지를 비교하면 차이가 분명해진다.

```kt
val policy: DiscountPolicy = AmountDiscountPolicy(Money.wons(800), SequenceCondition(1))

// ① 오버라이딩 → 지연 바인딩
policy.calculateDiscountAmount(screening)   // AmountDiscountPolicy의 구현이 실행됨

// ② 오버로딩 → 초기 바인딩
fun describe(p: DiscountPolicy) = "할인 정책"
fun describe(p: AmountDiscountPolicy) = "금액 할인 정책"
describe(policy)   // "할인 정책" ← 실제 객체가 아니라 선언 타입으로 고름

// ③ 확장 함수 → 초기 바인딩
fun DiscountPolicy.name() = "DiscountPolicy"
fun AmountDiscountPolicy.name() = "AmountDiscountPolicy"
policy.name()      // "DiscountPolicy"
```

①만 실제 객체를 따라가고 ②와 ③은 선언 타입을 따라간다.

> **`Main.kt`에서 `AmountDiscountPolicy`라고 적었으니 초기 바인딩 아닐까?**
> `Main.kt`가 정하는 것은 **어떤 객체를 만들어 넣을지**(실행 시간 의존성, 의존성 주입)이고 바인딩은 **메서드를 호출하는 줄**(`Movie.kt`)에서 일어난다. 바인딩은 프로그램 전체가 아니라 호출 한 줄 단위로 따진다.
> `Main.kt`를 읽는 **사람**은 아바타에 금액 할인이 들어간다는 것을 알지만 **호출하는 코드**인 Movie는 모른다. 그래서 정책을 설정 파일이나 사용자 입력으로 고르게 바꿔도 `Movie.kt`는 고칠 필요도, 다시 컴파일할 필요도 없다.

| | 초기 바인딩 | 지연 바인딩 |
|---|---|---|
| 장점 | 코드만 읽어도 무엇이 실행될지 안다. 컴파일러가 최적화하기 쉽다 | 호출하는 코드를 고치지 않고 구현을 추가하거나 교체할 수 있다 |
| 단점 | 새 행동을 추가하려면 호출하는 코드를 고쳐야 한다 | Movie만 읽어서는 무엇이 실행될지 모른다. 추적과 디버깅이 어렵다 |

앞에서 본 "코드의 의존성과 실행 시점의 의존성이 다를수록 유연하지만 이해하기 어렵다"는 문장이 사실상 지연 바인딩의 트레이드오프다. 코틀린이 클래스와 메서드를 기본적으로 `final`로 두는 것도 지연 바인딩이 필요한 곳에만 `open`/`abstract`/`interface`로 의도를 드러내라는 설계다.

> JVM의 JIT 컴파일러는 한 호출 지점에 늘 같은 타입만 들어오면 직접 호출로 바꿔 최적화하기도 한다(devirtualization). 이것도 실행 중에 관찰한 결과를 바탕으로 한 성능 최적화일 뿐이라 언어 차원에서는 여전히 지연 바인딩이다.

### 구현 상속과 인터페이스 상속

| 구분 | 다른 이름 | 목적 |
|---|---|---|
| **구현 상속** | 서브클래싱(subclassing) | 순수하게 코드를 재사용 |
| **인터페이스 상속** | 서브타이핑(subtyping) | 부모와 자식이 인터페이스를 공유해 다형적으로 협력 |

> 상속은 구현 상속이 아니라 **인터페이스 상속**을 위해 써야 한다. 구현을 재사용할 목적으로 상속을 쓰면 변경에 취약한 코드가 될 가능성이 높다.

### 인터페이스와 다형성

구현은 공유할 필요 없이 인터페이스만 공유하고 싶을 때가 있다. 자바와 코틀린은 이럴 때 쓰는 `interface`를 제공한다. C++처럼 인터페이스 문법이 따로 없는 언어에서는 추상 기반 클래스(abstract base class)로 같은 효과를 낸다.

할인 조건이 그런 경우다. 순서 조건과 기간 조건은 공유할 구현 없이 `isSatisfiedBy` 메시지만 이해하면 되니 `DiscountCondition`은 인터페이스로 만든다.

```kt
interface DiscountCondition {
    fun isSatisfiedBy(screening: Screening): Boolean
}
```

## 5. 추상화와 유연성

### 추상화의 힘

`DiscountPolicy`는 `AmountDiscountPolicy`, `PercentDiscountPolicy`보다 추상적이고 `DiscountCondition`은 `SequenceCondition`, `PeriodCondition`보다 추상적이다. 구체적인 구현은 자식에게 맡기고 **인터페이스(메시지)에 초점을 맞추기 때문**이다.

- `DiscountPolicy`: 모든 할인 정책이 받을 수 있는 `calculateDiscountAmount` 메시지를 정의한다.
- `DiscountCondition`: 모든 할인 조건이 받을 수 있는 `isSatisfiedBy` 메시지를 정의한다.

추상화를 쓰면 두 가지를 얻는다.

1. **세부 내용을 무시하고 상위 정책을 간단하게 표현할 수 있다.** 구체적인 정책과 조건을 몰라도 "영화 예매 요금은 최대 하나의 할인 정책과 여러 개의 할인 조건으로 계산한다"는 한 문장으로 도메인의 핵심을 설명할 수 있다. 디자인 패턴과 프레임워크도 모두 추상화로 상위 정책을 정의하는 이 메커니즘을 활용한다.
2. **설계가 유연해진다.** 상위 정책을 추상화로 표현하면 기존 구조를 고치지 않고도 새 기능을 추가하고 확장할 수 있다.

이 장의 코드는 2번을 단계별로 보여 준다.

| 단계 | 패키지 | 변경한 점 | 변경 이유 |
|---|---|---|---|
| 01 | `step01` | 기본 구현. 할인 정책 없음은 `null`로 표현 | (개선 전) Movie가 `null` 검사로 예외를 처리 |
| 02 | `step02` | `NoneDiscountPolicy` 추가, `null` 제거 | 할인 금액을 정하는 책임을 DiscountPolicy에 일관되게 두려고 |
| 03 | `step03` | `DiscountPolicy`를 인터페이스로, 기존 로직은 `DefaultDiscountPolicy`로 분리 | NoneDiscountPolicy가 쓰지 않는 구현을 물려받지 않게 하려고 |
| 04 | `step04` | `Movie.changeDiscountPolicy()` 추가 | 합성이면 실행 중에 정책을 바꿀 수 있음을 보이려고 |

### 유연한 설계: NoneDiscountPolicy (step02)

step01은 할인 정책이 없는 경우를 **예외 케이스**로 다룬다.

```kt
fun calculateMovieFee(screening: Screening): Money {
    if (discountPolicy == null) {
        return fee
    }
    return fee.minus(discountPolicy.calculateDiscountAmount(screening))
}
```

할인 금액을 계산하는 책임은 원래 DiscountPolicy 쪽에 있는데, 할인 정책이 없을 때만 Movie가 DiscountPolicy에 묻지 않고 직접 처리한다(`fee`를 그대로 반환). 정책이 있으면 DiscountPolicy에게 묻고, 없으면 Movie가 스스로 처리하니 같은 종류의 책임이 두 곳에 흩어져서 협력 방식이 일관되지 않다.
이 판단도 DiscountPolicy가 맡는 편이 일관적이니 "할인 정책 없음"도 하나의 할인 정책으로 본다.

```kt
class NoneDiscountPolicy : DiscountPolicy() {
    override fun getDiscountAmount(screening: Screening): Money = Money.ZERO
}
```

이제 Movie는 정책이 있든 없든 똑같이 DiscountPolicy에게 할인 금액을 묻는다. 스타워즈에는 `null` 대신 `NoneDiscountPolicy()`를 넘긴다.
"없음"을 아무 일도 하지 않는 객체로 표현하는 이런 방식을 **널 객체 패턴(Null Object pattern)**이라고 부른다.

책이 강조하는 것은 확장 방식이다. "할인 정책 없음"이라는 기능을 추가할 때 Movie나 DiscountPolicy를 고치지 않고 **NoneDiscountPolicy라는 클래스 하나만 추가해서** 애플리케이션을 확장했다. (우리 코드는 step01에 이미 `null` 분기가 있었기 때문에, step02에서 그 분기를 걷어 냈다.)

> 유연성이 필요한 곳에 추상화를 사용하라.

### 추상 클래스와 인터페이스 트레이드오프 (step03)

step02에는 어색한 점이 있다. NoneDiscountPolicy에는 할인 조건이 없으므로 부모의 `calculateDiscountAmount`는 반복문을 한 번도 돌지 않고 바로 `Money.ZERO`를 돌려준다. NoneDiscountPolicy의 `getDiscountAmount`는 결국 **한 번도 호출되지 않는다.** 무엇을 반환하도록 구현해도 결과가 같다.

NoneDiscountPolicy를 작성하는 개발자로서는 "조건이 없으면 부모가 getDiscountAmount를 부르지 않는다"는 부모의 내부 사정을 알아야 한다. 부모 클래스와 자식 클래스가 **개념적으로 결합**되어 있다.

해결 방법은 DiscountPolicy를 인터페이스로 바꾸고 기존 추상 클래스를 `DefaultDiscountPolicy`로 분리하는 것이다. 조건 검사 로직은 이 추상 클래스에 남는다. NoneDiscountPolicy는 `getDiscountAmount`가 아니라 인터페이스의 `calculateDiscountAmount`를 직접 오버라이딩한다.

```text
step02                                    step03
DiscountPolicy (추상 클래스)               DiscountPolicy (인터페이스)
 ├─ AmountDiscountPolicy                   ├─ DefaultDiscountPolicy (추상 클래스, 조건 검사 로직)
 ├─ PercentDiscountPolicy                  │   ├─ AmountDiscountPolicy
 └─ NoneDiscountPolicy                     │   └─ PercentDiscountPolicy
                                           └─ NoneDiscountPolicy
```

```kt
interface DiscountPolicy {
    fun calculateDiscountAmount(screening: Screening): Money
}

abstract class DefaultDiscountPolicy(vararg conditions: DiscountCondition) : DiscountPolicy {
    private val conditions: List<DiscountCondition> = conditions.toList()

    // 기존 템플릿 메서드. final로 막아 자식이 흐름을 바꾸지 못하게 한다.
    final override fun calculateDiscountAmount(screening: Screening): Money { /* 조건 검사 */ }

    protected abstract fun getDiscountAmount(screening: Screening): Money
}

class NoneDiscountPolicy : DiscountPolicy {
    override fun calculateDiscountAmount(screening: Screening): Money = Money.ZERO
}
```

이렇게 바꿔도 Movie는 한 줄도 고치지 않았다. Movie는 처음부터 DiscountPolicy라는 인터페이스에만 의존했기 때문이다.

> **코틀린에서 주의할 점: `final override`**
> step02까지 `calculateDiscountAmount`는 그냥 `fun`이라 기본적으로 `final`이었다. 그런데 코틀린에서 `override`한 멤버는 기본적으로 **다시 재정의할 수 있는(open) 상태**가 된다. 그대로 두면 자식이 조건 검사 흐름 자체를 덮어써서 템플릿 메서드 구조가 깨질 수 있다. 그래서 `final override`로 막는다.

그렇다면 항상 인터페이스로 바꿔야 할까? 책도 그렇지 않다고 말한다. 이상적으로는 인터페이스를 쓰는 설계가 더 낫지만 현실적으로는 NoneDiscountPolicy 하나 때문에 인터페이스를 추가하는 것이 과하다고 느낄 수도 있다.

> 구현과 관련된 모든 것은 트레이드오프의 대상이다. 작성하는 모든 코드에는 합당한 이유가 있어야 한다.

## 6. 코드 재사용: 상속과 합성

코드를 재사용하는 방법이 상속만 있는 것은 아니다.

**합성(composition)**: 다른 객체의 인스턴스를 자신의 인스턴스 변수로 포함해서 재사용하는 방법. Movie가 DiscountPolicy를 필드로 갖고 할인 계산을 맡기는 것이 합성이다.

흔히 코드를 재사용할 때는 상속보다 합성이 더 좋은 방법이라고 한다. 비교를 위해 Movie를 상속으로 설계해 보자.

```kt
abstract class Movie(...) {
    fun calculateMovieFee(screening: Screening): Money = fee.minus(getDiscountAmount(screening))
    protected abstract fun getDiscountAmount(screening: Screening): Money
}

class AmountDiscountMovie(...) : Movie(...) { ... }   // 금액 할인 영화
class PercentDiscountMovie(...) : Movie(...) { ... }  // 비율 할인 영화
```

이 설계도 동작은 하지만 상속에는 두 가지 문제가 있다.

### 상속의 문제 ①: 캡슐화를 위반한다

상속을 쓰려면 부모 클래스의 내부 구조를 잘 알아야 한다. AmountDiscountMovie를 만드는 개발자는 Movie의 `calculateMovieFee`가 내부에서 `getDiscountAmount`를 호출한다는 사실을 알고 있어야 한다.

부모의 구현이 자식에게 드러나므로 캡슐화가 약해진다.

```text
캡슐화 약화 → 자식이 부모에 강하게 결합 → 부모를 바꾸면 자식도 바뀔 가능성이 커짐
```

상속을 과하게 쓴 코드가 변경하기 어려운 이유다.

### 상속의 문제 ②: 설계가 유연하지 못하다

> 상속은 부모 클래스와 자식 클래스 사이의 관계를 컴파일 시점에 결정한다. 그래서 실행 시점에 객체의 종류를 변경하는 것이 불가능하다.

- **컴파일 시점에 결정된다**: "AmountDiscountMovie는 금액 할인으로 계산한다"는 사실이 코드를 작성할 때 클래스 선언에 고정된다.
- **실행 시점에 바꿀 수 없다**: `val avatar = AmountDiscountMovie(...)`로 만든 객체는 끝까지 AmountDiscountMovie다. 코틀린과 자바를 비롯한 대부분의 언어는 이미 만든 객체의 클래스를 바꾸는 기능을 지원하지 않는다.

아바타를 실행 중에 비율 할인으로 바꾸려면 PercentDiscountMovie 객체를 새로 만들고 기존 객체의 상태를 복사하는 수밖에 없다. 게다가 이미 만들어 둔 Screening들은 여전히 옛 객체를 가리키므로 그것들도 찾아서 바꿔야 한다.
비유하면 상속은 **렌즈 일체형 카메라**다. 망원 렌즈가 필요하면 카메라를 새로 사야 한다.

> 헷갈리기 쉬운 점: 변수에 AmountDiscountMovie를 넣을지 PercentDiscountMovie를 넣을지는 실행 중에 고를 수 있다. 실행 중에 바꿀 수 없는 것은 **이미 만들어진 객체의 클래스**다.

### 합성 (step04)

합성을 쓰면 두 문제가 모두 풀린다.

- **구현을 캡슐화한다**: Movie는 DiscountPolicy의 내부 구현을 모르고 `calculateDiscountAmount`라는 **메시지(인터페이스)**를 통해서만 재사용한다.
- **유연하다**: 의존하는 인스턴스를 바꾸기 쉽다. **렌즈 교환식 카메라**처럼 몸체(Movie)는 그대로 두고 부품(할인 정책)만 갈아 끼운다.

```kt
class Movie(..., private var discountPolicy: DiscountPolicy) {
    fun changeDiscountPolicy(discountPolicy: DiscountPolicy) {
        this.discountPolicy = discountPolicy
    }
}

avatar.changeDiscountPolicy(PercentDiscountPolicy(0.1, PeriodCondition(...)))
```

step04를 실행하면 같은 아바타 객체, 같은 상영 조건(월 10:30, 3회차, 2명)인데도 요금이 정책 변경 전 18,400원에서 변경 후 18,000원으로 달라진다.

| 구분 | 상속 | 합성 |
|---|---|---|
| 재사용 방식 | 부모 클래스의 구현을 물려받음 | 다른 객체의 인터페이스(메시지)를 사용 |
| 결합 방식 | 클래스를 통해 강하게 결합 | 메시지를 통해 느슨하게 결합 |
| 캡슐화 | 부모의 내부 구현이 자식에게 드러남 | 내부 구현을 몰라도 됨 |
| 할인 방식이 저장된 곳 | 객체의 **클래스** (생성할 때 고정) | 객체의 **필드** (다시 대입 가능) |
| 관계를 맺는 대상 | 클래스 ↔ 클래스 (코드에 적혀 컴파일 시점에 고정) | 객체 ↔ 객체 (실행 중에 참조로 연결) |
| 실행 중 교체 | 불가능. 객체를 새로 만들어야 함 | 가능. 부품만 교체 |

> **헷갈리기 쉬운 점: 상속 버전도 지연 바인딩이다.**
> AmountDiscountMovie 설계에서도 `calculateMovieFee` 안의 `getDiscountAmount` 호출은 실행 시점에 실제 객체를 보고 연결된다. 상속과 합성의 차이는 바인딩 방식이 아니라 **"어떤 구현을 쓸지"가 어디에 저장되어 있느냐**다. 클래스는 객체를 만든 뒤 바꿀 수 없지만 필드에 든 값은 바꿀 수 있다.

### "실행 시점에 바꾼다"는 무슨 뜻일까

"아바타를 비율 할인으로 바꿔 달라"는 요구라면 `Main.kt`의 생성 코드 한 줄만 고치면 된다. 상속이든 합성이든 마찬가지다.

```kt
val avatar = PercentDiscountMovie("아바타", ...)                 // 상속: 클래스만 바꿈
val avatar = Movie("아바타", ..., PercentDiscountPolicy(...))    // 합성: 인자만 바꿈
```

하지만 이것은 **프로그램을 실행하기 전에 소스 코드를 고친 것**이다. "실행 시점에 바꾼다"는 말은 **프로그램이 켜져 있는 동안** 바꾼다는 뜻이다. 지금 `Main.kt`는 실행하자마자 예매 몇 건을 출력하고 끝나기 때문에 둘의 차이가 보이지 않는다. 오래 켜져 있는 프로그램이어야 의미가 생긴다.

영화 예매 서버가 24시간 돌아가는데 관리자가 "지금부터 아바타 10% 할인"을 누르는 상황을 생각해 보자.

- **코드를 고치는 방식**: 개발자가 수정 → 빌드 → 배포 → 서버 재시작. 이벤트가 매일 바뀌거나 지점마다 다르면 그때마다 배포해야 한다.
- **합성**: 서버를 끄지 않고 `avatar.changeDiscountPolicy(...)` 한 번으로 정책을 바꾼다. 아바타의 모든 상영이 다음 예매부터 새 정책으로 계산된다(아래에서 설명).
- **상속**: 실행 중에 PercentDiscountMovie 객체를 새로 만들 수는 있다. 하지만 기존 `avatar`를 참조하던 Screening, 예매 목록, 캐시를 전부 찾아 바꿔야 하고, 하나라도 빠뜨리면 같은 영화가 곳에 따라 다르게 계산된다.

| | 개발 시점 변경 | 실행 시점 변경 |
|---|---|---|
| 누가 | 개발자 | 사용자, 관리자, 설정, 외부 입력 |
| 어떻게 | 소스 수정 → 빌드 → 배포 | 실행 중인 객체의 부품 교체 |
| 상속 | 생성 코드만 고치면 됨 | 새 객체를 만들고 참조를 전부 교체해야 함 |
| 합성 | 생성 코드만 고치면 됨 | 부품 교체 한 번 |

책이 말하는 합성의 장점은 오른쪽 열의 이야기다. 실행 중에 바꿀 일이 전혀 없는 프로그램이라면 이 장점은 실제로 의미가 없다.

### 정책 하나를 바꾸면 왜 모든 상영이 바뀔까

아바타는 하루에도 여러 번 상영된다. 서버 메모리에는 상영마다 Screening 객체가 있지만 **Movie 객체는 `avatar` 하나뿐**이다. 각 Screening은 Movie를 복사해서 갖고 있지 않고 같은 `avatar` 객체를 가리키는 **참조(화살표)**만 갖고 있다.

```kt
class Screening(private val movie: Movie, ...)   // Movie 객체 자체가 아니라 참조를 갖는다
```

```text
아바타 10:00 Screening ─┐
아바타 13:00 Screening ─┼──→ avatar (Movie 객체, 단 하나)
아바타 19:00 Screening ─┘        └ discountPolicy ──→ AmountDiscountPolicy
                                                 (교체 후) PercentDiscountPolicy
```

예매할 때 할인 정책을 읽는 경로는 다음과 같다.

```text
screening.reserve()
  → movie.calculateMovieFee()                    // 화살표를 따라 avatar에게 묻는다
    → discountPolicy.calculateDiscountAmount()  // avatar의 필드를 "그 순간" 읽는다
```

정책은 Screening마다 따로 있는 것이 아니라 `avatar` 안에 **필드 하나**로만 있다. 그리고 Screening은 예매할 때마다 그 필드를 새로 읽는다. 그래서 `avatar.changeDiscountPolicy(...)`로 필드 하나를 바꾸면 화살표로 `avatar`를 가리키는 Screening들은 따로 손대지 않아도 다음 예매부터 바뀐 정책을 쓴다.

비유하면 Screening들은 같은 공유 문서 링크를 들고 있는 사람들이다. 원본 문서 한 곳을 고치면 링크를 연 모든 사람이 고친 내용을 본다.

- **이미 끝난 예매는 그대로다.** `Reservation`은 예매 시점에 계산한 요금(`fee`)을 값으로 저장해 두기 때문이다.
- **상속은 이게 안 된다.** `avatar`의 클래스(AmountDiscountMovie)는 바꿀 수 없으니 새 객체를 만들어야 한다. 그러면 화살표들은 여전히 옛 객체를 가리키므로 Screening마다 찾아다니며 화살표를 새 객체로 돌려야 한다.

### 상속과 합성은 함께 쓴다

그렇다고 상속을 쓰지 말라는 뜻은 아니다. 이 장의 설계도 둘을 함께 쓴다.

- **Movie ↔ DiscountPolicy**: 합성. Movie는 할인 정책을 필드로 갖는다.
- **DiscountPolicy 계층**: 인터페이스 상속. 여러 할인 정책이 같은 인터페이스를 공유해 다형적으로 협력한다.

> 코드를 재사용할 때는 상속보다 합성을 선호하는 것이 옳다. 하지만 다형성을 위해 인터페이스를 재사용할 때는 상속과 합성을 함께 조합할 수밖에 없다.

## 7. 책과 다르게 생각한 점

### NoneDiscountPolicy까지 만드는 것은 과한 설계일까

"할인 없음" 하나 때문에 클래스를 따로 만드는 것이 과하다는 생각이 들 수 있다. 결론부터 말하면 **과한 설계는 아니지만 꼭 해야 하는 설계도 아니다.**

과한 설계로 보지 않는 이유는 세 가지다.

- 보통 과한 설계는 아직 없는 필요를 위해 **새 추상화**를 만드는 것이다. NoneDiscountPolicy는 이미 있는 DiscountPolicy의 빈자리 하나를 채울 뿐이다.
- "할인 없는 영화"는 지금 있는 요구사항이다. 미래를 가정한 것이 아니다.
- 몇 줄짜리 클래스 하나를 추가하는 대신 Movie의 예외 분기 하나가 사라진다. 복잡도를 더한다기보다 분기의 위치를 옮기는 일에 가깝다.

그래도 의문이 드는 이유도 있다.

- `null`을 처리하는 곳이 Movie 한 곳뿐이다. 코틀린에서는 아래 한 줄로 안전하게 처리할 수 있어서 클래스를 늘린 만큼의 이득이 작게 느껴진다.

  ```kt
  fee.minus(discountPolicy?.calculateDiscountAmount(screening) ?: Money.ZERO)
  ```

  다만 이 방식에서는 "할인이 없으면 0원"이라는 규칙을 여전히 Movie가 알고 있다. 책이 문제 삼는 것은 NPE 같은 안전성이 아니라 **책임의 위치**다.
- Movie 코드만 읽어서는 "할인 없음"이라는 경우가 있다는 것이 보이지 않는다.

| 상황 | 선택 |
|---|---|
| "없음"이 정상적인 도메인 개념이고 의미가 분명함 (0원) | 널 객체 |
| "없음"이 오류나 누락을 뜻함 | `null` + 명시적 처리, 또는 예외. 널 객체는 버그를 숨긴다 |
| `null` 처리가 한두 곳뿐이고 늘어날 것 같지 않음 | 코틀린 `?:`로 충분 |
| 그 타입을 쓰는 곳이 많거나, 다른 사람이 갖다 쓰는 라이브러리 | 널 객체 (예: SLF4J `NOPLogger`, Spring `NoOpCacheManager`) |

### changeDiscountPolicy는 자연스러운가

step04의 `changeDiscountPolicy()`는 실제 도메인 설계로 보면 어색하다. 합성이면 실행 중에 바꿀 수 **있다**는 가능성을 보여 주는 예시에 가깝다.

- **사실상 setter다.** 도메인 규칙 없이 필드를 통째로 바꾼다. 누가, 언제, 왜 바꾸는지가 코드에 드러나지 않는다.
- **영향 범위가 보이지 않는다.** 같은 Movie를 가리키는 모든 Screening의 요금이 한순간에 바뀐다. 이미 끝난 예매는 계산된 요금을 Reservation에 저장해 두므로 괜찮지만 아직 예매하지 않은 상영에 어떤 정책이 적용될지 추적하기 어렵다.
- **불변성과 부딪힌다.** `val`이던 필드를 이 메서드 때문에 `var`로 바꿔야 한다.

합성의 장점은 이 메서드가 없어도 그대로 남는다. 자식 클래스를 만들지 않고 **생성할 때 정책을 넣어 주는 것만으로** 영화마다 다른 할인 방식을 줄 수 있고(step03) 새 정책을 추가해도 Movie를 고칠 필요가 없다.
책이 말하는 "실행 시점에 바꿀 수 있다"는 설계가 그것을 **허용한다**는 뜻이다. 교체 메서드를 반드시 열어 두라는 말은 아니다.

| 상황 | 방법 |
|---|---|
| 정책을 바꿀 요구사항이 없음 | `val` 유지, 교체 메서드 없음 (step03 구조) |
| 정책 변경이 실제 요구사항 | 새 객체를 돌려주는 `withDiscountPolicy()`, 또는 "기간별 할인 정책"처럼 도메인 개념으로 표현 |
| 책의 개념을 보여 주는 예제 | 지금처럼 두되, 예시용임을 밝힘 |

### 실무에서는 할인 이벤트를 어떻게 여나

"오전 10시부터 모든 제품 20% 할인" 같은 이벤트는 할인을 자주 하는 서비스(커머스, 예매, 배달 등)라면 대부분 **소스 코드를 고치지 않고** 연다. 운영자가 백오피스(관리자 페이지)에서 프로모션을 등록하면 끝난다.

다만 앞의 `changeDiscountPolicy`처럼 메모리에 오래 살아 있는 객체의 필드를 바꾸는 방식은 아니다. 요즘 웹 서버는 보통 상태를 메모리에 오래 들고 있지 않고, **요청마다 DB에서 읽어 객체를 새로 조립**한 뒤 응답이 끝나면 버린다.

1. 운영자가 프로모션을 미리 등록한다.

   | id | type | value | start_at | end_at | target |
   |---|---|---|---|---|---|
   | 101 | PERCENT | 0.2 | 2026-11-13 10:00 | 2026-11-13 23:59 | ALL |

2. 서버는 요청이 올 때마다 유효한 프로모션을 조회해 정책 객체를 조립한다.

   ```kt
   class DiscountPolicyFactory {
       fun create(promo: Promotion): DiscountPolicy = when (promo.type) {
           AMOUNT  -> AmountDiscountPolicy(Money.wons(promo.value), PeriodCondition(promo.startAt, promo.endAt))
           PERCENT -> PercentDiscountPolicy(promo.value, PeriodCondition(promo.startAt, promo.endAt))
       }
   }

   // 가격 계산 코드는 DiscountPolicy 인터페이스만 안다
   val policy = factory.create(promotionRepository.findActive(product))
   val price = product.fee.minus(policy.calculateDiscountAmount(...))
   ```

3. 시간 조건은 코드가 알아서 처리한다. 9시 59분 요청은 기간 조건을 만족하지 못해 할인이 없고 10시 이후 요청부터 20%가 적용된다. 10시 정각에 누가 버튼을 누를 필요가 없다. 이 장의 `PeriodCondition`이 하는 일이 바로 이것이다.

실무에서 말하는 "실행 중에 바뀐다"는 대개 **DB의 데이터가 바뀌면 다음 요청부터 다른 정책 객체가 조립된다**는 뜻이다. 필드를 갈아 끼우는 대신 매번 새로 조립하지만, 이것이 가능하려면 할인 정책이 Movie나 Product의 클래스에 박혀 있지 않고 **따로 끼울 수 있는 부품**이어야 한다. 결국 합성 구조가 전제다. (캐시를 쓴다면 프로모션이 바뀔 때 캐시를 비우거나 TTL을 짧게 둬야 한다.)

그래도 코드를 고쳐야 하는 변경은 있다.

| 요구사항 | 처리 방법 |
|---|---|
| 할인율 20% → 30% | 데이터 수정 |
| 기간, 대상 상품 변경 | 데이터 수정 |
| 금액 할인 ↔ 비율 할인 | 데이터 수정 (`type` 컬럼) |
| **처음 보는 종류의 할인** (1+1, 세 번째 구매 무료, 첫 구매 고객 한정) | **코드 추가 → 배포** |

마지막 줄이 다형성과 OCP가 힘을 발휘하는 지점이다. 새 종류의 할인이 생기면 `BuyOneGetOneDiscountPolicy` 클래스 하나를 만들고 팩토리에 한 줄을 추가한다. 가격 계산 코드나 주문 코드는 건드리지 않는다.

- **이미 있는 종류의 할인을 새로 여는 것** → 데이터 (배포 없음)
- **새로운 종류의 할인을 만드는 것** → 코드 (새 클래스 추가, 기존 코드는 그대로)

모든 회사가 이렇게 하는 것은 아니다. 백오피스, 프로모션 테이블, 팩토리를 만드는 데도 비용이 든다. 1년에 할인을 두어 번 하는 작은 서비스라면 코드를 고쳐 배포하는 쪽이 오히려 합리적일 수 있다.

### 작은 프로젝트에서도 인터페이스와 구현을 분리해야 할까

기준은 **프로젝트 규모**가 아니라 **지금 실제로 갈라지는 지점이 있느냐**다. 작은 프로젝트에도 필요한 추상화가 있고 큰 프로젝트에도 쓸데없는 추상화가 있다.

영화 예제는 오버엔지니어링이 아니다. 구현 세 개(Amount, Percent, None)가 **지금 이미** 동시에 쓰이고 실행 중 교체까지 한다. 인터페이스 없이 만들면 Movie 안에 `when (discountType) { AMOUNT -> ...; PERCENT -> ...; NONE -> ... }` 분기가 생기고 정책이 늘 때마다 Movie를 고쳐야 한다.

오버엔지니어링은 이런 경우다.

```kt
interface UserService { fun findUser(id: Long): User }
class UserServiceImpl : UserService { ... }   // 구현은 영원히 이거 하나
```

구현이 하나뿐이고 두 번째가 생길 계획도 구체적이지 않다. 코드를 따라갈 때마다 인터페이스를 거쳐 구현체를 찾아가야 하니 지연 바인딩의 비용(추적이 어려움)은 그대로 내면서 유연성이라는 이득은 얻지 못한다. "혹시 몰라서" 만든 추상화이고 YAGNI(You Aren't Gonna Need It)에 걸린다.

실용적인 기준은 세 가지다.

1. **구현이 하나면 구체 클래스로 시작하고 두 번째가 생길 때 인터페이스를 추출한다.** 추출은 IDE 리팩터링으로 금방 끝난다. 실제 구현 두 개를 보고 나서 추출해야 진짜 공통점이 무엇인지 알 수 있어 추상화의 질도 좋아진다.
2. **구현이 하나여도 분리할 가치가 있는 경우가 있다.**

   | 경우 | 이유 |
   |---|---|
   | 외부 시스템 경계 (결제 API, 메일 발송, 외부 HTTP) | 테스트에서 가짜 구현으로 바꿔 끼우기 쉽고, 외부 변경이 도메인으로 번지지 않음 |
   | 시간·난수 (`Clock`, `Random`) | 테스트에서 값을 고정해야 함 |
   | 도메인 → 인프라 의존을 뒤집고 싶을 때 | 도메인 모듈이 DB나 프레임워크를 몰라도 되게 함 (의존성 역전) |
   | 다른 팀이나 외부에 공개하는 API | 계약을 구현과 따로 관리해야 함 |

   같은 모듈 안에서 혼자 부르는 서비스라면 대개 필요 없다. 코틀린에서는 mockk로 클래스도 바로 mock할 수 있어서 "테스트 때문에" 인터페이스를 만드는 경우도 줄어든다.
3. **인터페이스보다 가벼운 도구도 있다.**
   - **함수 타입**: 갈라지는 게 메서드 하나뿐이면 `(Screening) -> Money` 같은 람다로 충분할 때가 많다.
   - **sealed class + `when`**: 변형의 종류가 닫혀 있고 그 위에서 할 연산이 자주 늘어난다면 sealed class가 더 맞다. 새 타입을 자주 추가할 거라면 인터페이스, 새 연산을 자주 추가할 거라면 sealed class가 유리하다.

> 추상화는 공짜가 아니라 **가독성을 내고 유연성을 사는 거래**다. 변경이 지금 눈앞에 있으면 분리하고, 상상 속에만 있으면 미룬다.

---

2장은 영화 예매 시스템으로 객체지향 프로그래밍의 핵심 도구를 보여 준다.
객체들은 메시지로 협력한다. 상속과 다형성, 그리고 메시지와 메서드를 실행 시점에 연결하는 지연 바인딩 덕분에 Movie는 구체적인 할인 정책을 몰라도 요금을 계산한다. 추상화에 의존하면 NoneDiscountPolicy처럼 클래스를 추가하는 것만으로 기능을 확장할 수 있다. 합성을 쓰면 구현을 캡슐화하면서 실행 중에도 협력 상대를 바꿀 수 있다.
다만 인터페이스를 추가할지, 널 객체를 만들지, 교체 메서드를 열어 둘지는 모두 **트레이드오프**다. 작성하는 모든 코드에는 합당한 이유가 있어야 한다.
