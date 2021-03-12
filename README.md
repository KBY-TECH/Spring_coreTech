# OOP Spring 

## Intro.비즈니스 요구사항과 설계

- 회원
    1. 회원 가입하고 조회
    2. 회원은 일반,VIP 두가지 등급
    3. DB 미확정


- 주문 할인 정책
    1. 회원은 상품을 주문.
    2. 회원 등급 할인정책
    3. 할인정책(고정 금액 할인,확률적 할인(둘 중 하나)

![캡처](https://user-images.githubusercontent.com/67587446/110898127-003cd900-8342-11eb-9117-22a3c7042521.PNG)

--- ---

## 회원 도메인 생성

**인터페이스 작성**

```javascript

public
interfaceMemberService
{
    // 회원 가입
    void Join(Member
    member
)
    ;

    // 조회 (id를 통한 조회)
    MemberfindMember(Long
    memberId
)
    ;
}

```

TestCode

```javascript
class MemberServiceTest {

    MemberService
    memberService = new MemberServiceImpl();// 인터페이스를 바로보지 않음.

    @Test
    void

    Join() {
        //given
        Member
        member = new Member(1
        L, "KBY", Grade.VIP
    )
        ;
        //when
        memberService.Join(member);
        ;
        //then
        System.out.println(memberService.findMember(1
        L
    ).
        toString()
    )
        ;
    }

}
```

실질적인 인터페이스를 상속받아 serviceImpl 을 만든 후 테스트를 진행 해야 할떄 MemberService 라는 인터페이스를 상속받은 구현체를 직접 불러야 하는 상황을 맞이 하게 된다.<br><br>
DIP 위반 :`Memberservice memberservice =new MemberSeriveImpl();`<br><br>
<p style="color: indianred"> 이 경우에는 의존관계가 인터페이스 뿐만 아니라 구현체까지 모두 의존하는 문제점이 발생한다.</p>

## 주문 도메인 협력,역할,책임

    1. 주문 생성 : 클라이언트는 주문 생성 요청
    2. 회원 조회 : 할인 정책을 적용하기 위해 회원등급이 필요.
    3. 할인 적용 : 주문 서비스는 회원 등급에 따른 할인 여부를 할인 정책에 위임.
    4. 주문 결과 반환 : 할인 결과를 포함한 주문 결과를 반환.

cf.할인정책(둘 중 변경이 있을 수 있으므로 둘다 만든 후 테스트용으로 고정 할인 적용 우선.)
![캡처](https://user-images.githubusercontent.com/67587446/110901679-059d2200-8348-11eb-945a-f326fd9e6b6d.PNG)

- 주문 도메인
  <br><br>
  ![캡처](https://user-images.githubusercontent.com/67587446/110900683-7e9b7a00-8346-11eb-80d7-6a9181ffd02e.PNG)

<p style="color: cornflowerblue"> 다형성을 적용하여 객체를 자유롭게 갈아 끼울수 있도록 인터페이스를 통해 설계하여 유연하게 변경가능하다.</p>

**인터페이스 생성**

```javascript
public
interface
orderService
{
    Order
    createOrder(Long
    memberId, String
    itemName, int
    itemPrice
)
    ;
}
// 실제 orderserviceImpl
public

class orderServiceImpl implements orderService {
    private final
    MemberRepository
    memberRepository = new memoryMemberRepository();
    private final
    discountPolicy
    discountPolicy = new FixDiscountPolicy();
    private final
    discountPolicy
    discountPolicy = new rateDiscountPolicy();
=>
    구체클래스들에게
    의존
    OCP위반
.

    @Override
    public Order

    createOrder(Long

    memberId
,
    String
    itemName
,
    int
    itemPrice
) {

    Member
    member = memberRepository.findById(memberId);
    int
    discount = discountPolicy.discount(member, itemPrice); // 당일 책인 원칙.
    return
    new

    Order(memberId, itemName, itemPrice, discount);
}

}
```

주문 서비스 또한 인터페이스를 활용하여 주문 생성을 하는 바디 없는 메서드를 만든 후 상속받아 사용한다. 주문에서는 회원의 등급을 체크해야 하므로 회원 저장소가 필요하다.

**TestCode**

```javascript
public

class orderService {

    MemberService
    memberService = new MemberServiceImpl();
    orderService
    orderService = new orderServiceImpl();

    @Test
    void

    createOrder() {
        Long
        memberId = 1
        L;
        Member
        member = new Member(memberId, "name", Grade.VIP);

        Order
        order = orderService.createOrder(memberId, "item", 10000);
        Assert.assertEquals(order.getDiscountPrice(), 1000);
    }
}
```

개방 폐쇄 원칙 : 확장에는 열려 있으나 변경에는 닫혀있다. 라는 뜻으로 다형성을 활용하여 인터페이스 설계를 잘 설계 해놓는 것이 (클라이언트 코드 변경)많은 코드 변경없이 구현체를 자유롭게 갈아 끼울수 유연성이
있게 된다.

## 지금까지의 회고.

### 기대 결과

![캡처](https://user-images.githubusercontent.com/67587446/110905390-beb22b00-834d-11eb-9bfa-6718157ff413.PNG)

- 역할과 구현을 충실하게 분리함.(다형성을 활용하여 인터페이스 구현 객체 분리)
- OCP,DIP 설계 원칙을 준수 하려 했지만 아니된다. <br>
  DIP 위반 : 추상화에만 의존하는 것은 맞지만 구현클래스 또한 같이 의존하는 상황이다.<br>
  OCP 위반 :할인 정잭 부분에서 할인 정책이 고정 할인과 정률 할인 정책을 두가지 중 한가지를 정하기 위해
  <br>DiscountPolicy discount=new FixDiscount();
  <br>DiscountPolicy discount=new RateDiscount();

  <br> 구현 클래스를 교체해야하는 코드 변경이 꼭 필요한 상황이다.

-----

## 해결방안

누군가가 orderserviceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해줘야 한다.
<p style="color: chartreuse"> 관심사의 분리 : Container </p>
----------

### 대신 생성하고 넣어주는 설정 클래스 생성.

service 부분의 클래스들은 원래 생성자를 가지고 있다. 
여태껏 생성자를 따로 생성하지는 않고 사용해왔다. 각 service 들은 자신의 생성자를 통해 의존 관계 있는 클래스들을 new 를 하지 않고 생성과 동신의 의존관계 클래스들의 생성자들을 자동으로 주입받을 수 있어 NP가 발생하지 않는다.
 
    private MemberRepository memberRepository 
    private discountPolicy discountPolicy 
    private discountPolicy discountPolicy

### 설정 클래스 == 누군가 대신 수행해 주는 클래스.
```javascript
package core.core_tech;

// 실제 동작에 필요한 "구현객체를 생성"
public class AppConfig_DI {
    // 스프링 빈 저장소 IOC 컨테이너.
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    public MemberRepository memberRepository() {
        return new memoryMemberRepository();
    }

    public orderService orderService(){
        // 회원의 등급 조회를 통해 할인률을 결정하므로
        return new orderServiceImpl(memberRepository(), discountPolicy());
    }

    public discountPolicy discountPolicy(){
        return new FixDiscountPolicy();
    }
}
```
<br>

**각 서비스들의 생성자 구조**
```javascript
// 멤버서비스 생성자.
   public MemberServiceImpl(MemberRepository memberRepository) {
       this.memberRepository = memberRepository;
   }
// 오더서비스 생성자.
public orderServiceImpl(MemberRepository memberRepository, discountPolicy discountPolicy) {
  this.memberRepository = memberRepository;
  this.discountPolicy = discountPolicy;
}
```
Appconfig 라는 외부 클래스를 통해 생성자를 주입받으며 직접적인 코드 변경없이 service들은 추상화,구현체에 의존하는 것이 아닌 추상화에 의존하게 되어 DIP원칙에 위배 되지 않고 코드 변경 또한 하지 않아도 된다.
외부 클래스 Appconfig 를 통해 해당 부품들을 유연하게 변경이 가능하도록 설정 되었다.
<br><br>

![캡처](https://user-images.githubusercontent.com/67587446/110908117-b3f99500-8351-11eb-86af-f6378828355b.PNG) <br>
**각 테스트 코드는 new 를 통해 객체를 직정 생성하지 않고 외부에 설정한 AppConfig객체를 생성하여 주입받을 수 있도록 하면 된다.**

## 문제점 해결 완료.
  <p style="color: cornflowerblue"> 외부 클래스를 통해 관심사를 분리하여 각 서비스들은 담당 기능을 실행하는 책임만을 갖도록 하였다.</p>
  위에서 언급한 해결방법을 통하여 orderService,memberService 자체들이 직접 구현 클래스를 가져오는 기능을 하지 않아도 된다.

![캡처](https://user-images.githubusercontent.com/67587446/110908609-6a5d7a00-8352-11eb-8f3f-cd51c6aadc62.PNG)


## 요약정리
**클라이언트객체(service 들)은 직접 구현 객체를 생성하고 연결하고 등의 다양한 책임을 맡고 있었다.
하지만 단일 책인 원칙을 따르면서 새로운 사람을 고용하였다.(관심사 분리<AppConfig class 가 객체 생성 연결하는 담당>)
그러므로 각 클라이언트 객체들은 실행하는데 초첨을 두고 일에 집중을 할 수 있게 된다.**


- <u>SRP</u> 
   - **클라이언트객체(service 들)은 직접 구현 객체를 생성하고 연결하고 등의 다양한 책임을 맡고 있었다.
  하지만 단일 책인 원칙을 따르면서 새로운 사람을 고용하였다.(관심사 분리<AppConfig class 가 객체 생성 연결하는 담당>)
  그러므로 각 클라이언트 객체들은 실행하는데 초첨을 두고 일에 집중을 할 수 있게 된다.**

- <u>DIP</u>
    - **추상화와 구현클래스 모둘를 의존하는 관계에서 추상화에 의존하게만 되었다.(new 를 통해 직접 할 필요 없다.)**
- <u>OCP</u>:
    - **의존 관계를 직접 변경하지않는다.(클라이언트 코드에 주입하므로)**
    - **확장하여도 사용 영역의 변경은 닫혀 있게 된다.**




