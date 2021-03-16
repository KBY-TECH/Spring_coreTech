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

**TestCode**

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
public interface orderService
{
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
// 실제 orderserviceImpl
public class orderServiceImpl implements orderService {
    private final MemberRepository memberRepository = new memoryMemberRepository();
    private final discountPolicy discountPolicy = new FixDiscountPolicy();
    private final discountPolicy discountPolicy = new rateDiscountPolicy();
// => 구체클래스들에게 의존 OCP위반
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
  

## 의존관계 주입
  - 애플리케이션이 실행시점에 외부에서 구현체들을 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 
    의존 관계가 연결되는 것을 의존관계 주입이라고 한다.
  - 객체 인스턴스를 생성, 그 참조값을 전달.
  - 의존관계 주입을 사용하면 <d style="color:cornflowerblue"> 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.
  - 의존관계 주입을 사용하면 정적 클래스 의존관계를 변경하지 않고 **<d style="color:cornflowerblue">동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.</P>**

## Ioc 컨테이너, DI 컨테이너
  - appConfig 처럼 객체를 생성하고 관리하면서 의존 관계를 연결해 주는 것이 IoC컨테이너 또는 DI 컨테이너라 한다.
  - 또는 어셈블러, 오브젝트 팩토리라고 칭한다.


--- --- 

# 스프링 전환

```javascript
// 실제 동작에 필요한 "구현객체를 생성"
@Configuration
public class AppConfig_DI {
    // 스프링 빈 저장소 IOC 컨테이너.
    @Bean
    public MemberService memberService(){
        System.out.println("AppConfig_DI.memberService");
        return new MemberServiceImpl(memberRepository()); // 생성자 주입 DIP 원칙 준수
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("AppConfig_DI.memberRepository");
        return new memoryMemberRepository();
    }

    @Bean
    public orderService orderService(){

        System.out.println("AppConfig_DI.orderService");
        return new orderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public discountPolicy discountPolicy(){
        return new rateDiscountPolicy();
    }
}
```


**이전에  @Configuration(구성) , @Bean 이런 애노테이션을 붙이기 이전에는 컨테이너의 역할이 무엇인지 알기위해 순수 자바 코드로만 작성한 것이였다.**<br><br>
**이제는 스프링에서 제공해주는 컨테이너 라는 것을  @Configuration(구성) 이 애노테이션을 붙여줌으로써 비로서 스프링에서 제공하는 컨테이너가 되는 것이다.**

**이전에는 AppConfig를 통해 조회했지만, 이제는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고, 컨테이너 안에서 빈을 찾아 사용하도록 변경되었다.**
<br>

> @Configuration(구성) , @Bean 애노테이션을 붙여준다.
> 
>   ※ 빈이란 ?
>     스프링 컨테이너에 등록된 객체를 스프링 빈이라고 한다.    
> 
> 각 서비스들은 컨테이너인 pplicationConText객체를 생성하여 app.getBean(빈 메소드명,빈 빈 객체 타입)을 통해 직접 주입 받을 수 있다.


## 컨테이너의 생성 동작 원리(그림 설명)

### 1. 생성
![캡처](https://user-images.githubusercontent.com/67587446/111299739-a4e94e80-8693-11eb-877b-dffedc633f65.PNG)

애노테이션을 붙인 후 AppConfig class 컨테이너로서 스프링 빈 저장소를 가지고 있게 된다.

### 빈 등록
![캡처](https://user-images.githubusercontent.com/67587446/111300158-1cb77900-8694-11eb-8f89-01c0fff3cfb2.PNG)

### 빈 의존관계 설정 - 준비

![z](https://user-images.githubusercontent.com/67587446/111300391-5f795100-8694-11eb-921b-bd92eb31fc81.PNG)

### 의존관계 설정 - 완료
![캡처](https://user-images.githubusercontent.com/67587446/111300577-951e3a00-8694-11eb-9076-b1184c436920.PNG)

###빈 조회 방법
  ac.getBean("빈이름","객체 타입(인터페이스 타입 또는 구체 타입)")
  - 이름과 타입으로 조회
    - 이름 조회
    - 타입 조회
  - 상속관계 에서의 빈 조회.
    - 부모 타입으로 조회시 자식 타입 또한 함께 조횐된다.
    - 자식이 둘 이상이면 NoUniqueBeanDefinitionException 오류가 난다.(구체 타입으로 조히 권고))
  
## BeanFactory 와 ApplicationContext 관계( 둘 다 컨테이너)
    BeanFactory > ApplicationContext

  - ApplicationContext 는 BeanFactory의 자식이다.
  - ApplicationContext 에서 사용한 getBean은 BeanFactory 에서 물려받은 기능이다.
  - ApplicationContext 는 BeanFactory의 외 다양한 IFS 들을 가지고 있다.
  - 애플리케이션을 개발할 때는 빈을 관리하고 조회하는 기능 외에 수많은 부가 기능이 필요하다.
    - 메시지 소스 활용(국제화 기능)
    - 환경 변수
    - 애플리케이션 이벤트
    - 편리한 리소스 조회
  
## 스프링 빈 설정 메타 정보 -BeanDefinition
  - 다양한 형태의 설정 정보를 BeanDefinition으로 추상화 해서 사용한다.
  - info
    - BeanClassName
    - factoryBeanName
    - factoryMethodName
    - Scope : singlton(Default)
    - lazyInit : 실제 빈을 사용할 때 까지 최대한 생성을 지연 처리.
    - initMethodName : 빈 생성 -> 읜존관계 적용 뒤에 호출되는 초기화 메서드 명
    - DestoryMethodNam : 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명
    - Constructor arguments, Properties : 의존관계 주입에서 사용
  


--- ---
# 싱글톤 컨테이너

    문제점
    처음 순수하게 컨테이너 역할을 한 AppConfig (어노테이션이 없는) 는 한 고객의 요청 마다 객체를 새로 생성한다.
    
**해결 : 하나의 객체를 공유하여 사용하도록 설계한다.(싱글톤 패턴)**


## 싱글톤 패턴
  싱글톤이란?
  > 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다.
  > 2개이상의 인스턴스 생성을 못하도록 private으로 생성자를 선언한다(new 사용 못하도록)
```javascript

public class singletonService   {
    private static final  singletonService ins=new singletonService(); // 자기 자신 내부에 private 에 하나 static 하나에 올라옴

    // 조횟 사용
    public static  singletonService getIns(){
        return ins;
    }

     private singletonService() {
    }

    public void logic(){}
    {
        System.out.println("싱글톤 객체 로직 호출.");
    }
}
```

private 을 통해 new를 막아 놓았다. 


```javascript
 @Test
    @DisplayName("싱글톤 패턴 적용하는 객체 사용")
    void singleTonService()
    {
//        new singletonService();컴파일 오류
        singletonService s1=singletonService.getIns();
        singletonService s2=singletonService.getIns();
        singletonService s3=singletonService.getIns();

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        Assertions.assertThat(s1).isSameAs(s2);
        Assertions.assertThat(s2).isSameAs(s3);
        Assertions.assertThat(s1).isSameAs(s3);


    }

```


**몇 개를 생성해도 같은 객체이다.**

싱글톤 패턴을 적용하여 수 많은 고객의 요청이 올 때마다 객체를 생성하는 것이 아닌 이미 만들어진 객체를
공유해서 효울적으로 사용할 수 있다. 하지만 싱글톤 패턴은 다음과 같은 수 많은 문제점들을 가지고 있다.


<div style="color: indianred">
구현코드 자체가 많다.<br>
클라이언트가 구체 클래스에 의존한다. ex) singletonService.getIns();<br>
구체 클래스의 의존으로 OCP 위반할 가능성이 있다.<br>
테스트 하기 복잡하다.<br>
내부 속성 변경 초기화가 어렵다.<br>
private 생성자로 자식 클래스 만들기가 어렵다.<br>
유연성이 떨어진다.<br>
안티패턴으로 불리운다.<br>
</div>
  

## 싱글톤 컨테이너
        스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하면서, 인스턴스를 1개만 생성하여 관리한다.
        지금까지 (스프링 컨테이너에 등록된 객체)빈들은 싱글톤으로 관리 되는 빈이다.

```javascript
 @Test
    @DisplayName("스프링 있는 싱글톤 패턴으로 하나의 객체를 공유하기")
    public void singleTonexeTest()
    {
        // DEFAUL == SINGLETON .
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig_DI.class);
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        MemberService memberService2=applicationContext.getBean("memberService",MemberService.class);
        System.out.println(memberService);
        System.out.println(memberService2);
        Assertions.assertThat(memberService).isSameAs(memberService2);
        // result 1개로 공유
       /*
       result 
          core.core_tech.domain.member.service.MemberServiceImpl@2c5529ab
          core.core_tech.domain.member.service.MemberServiceImpl@2c5529ab
        */
    }
```

### 장점
    싱글턴 패턴을 따로 적용하지 않아도 default로 싱글톤으로 관리된다.
    스프링 컨테이너는 싱글톤 컨테이너 역할을 한다.(싱글톤 레지스트리 라고 한단)
    싱글턴 패턴의 단점을 해결한다.
       - 코드가 들어가지 않아도 되며, DIP,OCP,테스트,private 생성자로 부터 자유롭게 싱글톤을 사용할 수 있다.

### 싱글톤 방식의 주의점 
여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(Stateful)하게 설계하면 안된다.
- <p style="color: cornflowerblue">무상태(Stateless)로 설계 해야 한다.</p>

    - 특정 클라이언트에 의존적 필드가 존재해선 안된다.
    - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
    - Read만 가능해야 한다.
    - 필드 대신에 자바에서 공유되지 않는, 지역변수,파라미터,TreadLocal등을 사용해야 한다.
    - 필드에 공유 값을 설정하면 큰 장애를 유발한다.
    

```javascript
public class statefulService {
   private int price; //상태 유지하는 필드

    public int order(String name,int price)
    {
        System.out.println("name : "+name+"price :"+price);
        return price;
    }

   public int getPrice()
   {
       return this.price;
   }
}


@Test
void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(Testconfig.class);
    statefulService s1 = ac.getBean(statefulService.class);
    statefulService s2 = ac.getBean(statefulService.class);

    int a = s1.order("userA", 10000);
    int b = s2.order("userB", 30000);

    System.out.println(a);
    System.out.println(b);

    assertThat(a).isEqualTo(10000);

}

```
**해당 결과는 False가 난다.
만약 Thread를 사용하게 되면 price 라는 공유 필드로 인해 맨 마지막에 인스턴스가 생성된 30000이 되어진다.
기대하는 결과 값과 달라지는 이유는 인스턴스는 한개로 공유(싱글톤)되기 때문이다.**


※ AppConfig 클래스 안에서 memberService, orderService 는 memberRepository를 의존하고 있어 new 2번을 통해 객체를 2번 생성하는 것인가 라는 의문을 가질수 있다.


```javascript
@Configuration
public class AppConfig_DI {
    // 스프링 빈 저장소 IOC 컨테이너.
    @Bean
    public MemberService memberService(){
        System.out.println("AppConfig_DI.memberService");
        return new MemberServiceImpl(memberRepository()); // 생성자 주입 DIP 원칙 준수
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("AppConfig_DI.memberRepository");
        return new memoryMemberRepository();
    }

    @Bean
    public orderService orderService(){

        System.out.println("AppConfig_DI.orderService");
        return new orderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public discountPolicy discountPolicy(){
        return new rateDiscountPolicy();
    }
}

```

<d style="color: cornflowerblue">
위에 언급했듯이 스프링 컨테이너는 싱글톤 컨테이너 이므로 하나의 인스턴스를 공유하는 것으로 알려져있어 new 통해 객체를 생성하여도
하나의 인스턴스에 의해서 공유 된다.
</d>

### @Configuration 과 바이트코드
    싱글톤 컨테이너는 싱글톤 레지스트리로서 빈들이 싱글톤임을 보장해야 한다.
    AppConfig를 보면 2번 호출되어서 생성해야 되는 memberRepository가 sout이 출력되지 않는다.

#### TEST
```javascript
 @Test
    void configuration()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig_DI.class);

        AppConfig_DI bean=ac.getBean(AppConfig_DI.class);

        System.out.println("statefulServiceTest.configuration");
        System.out.println("bean is "+bean.getClass()); //$$EnhancerBySpringCGLIB$$41169ffc

    }
```

**AnnotationConfigApplicationContext의 인자로 들어간 AppConfigDI 또한 빈으로 등록되어 조회가 가능하다.
빈을 조회해 보면 순수 클래스라면 class core.core_tech.AppConfigDI 라고 출력되어져야 할 빈정보가
xxxCGLIB 가 붙으면서 ~~라고 적혀있는 것을 확인할 수 있다.(@Configuration)
xxxCGLIB 는 본체인 AppConfigDI를 상속받은 임시 객체 이다. 즉 프록시이다. 그 프록시를 빈으로 등록한 것이다.**

#### 그 임의의 다른 클래스(프록시)가 싱글톤이 보장되도록 해준다.
    프록시 객체 안에서는 memberRepository 가 빈으로 등록되어있으면 
    똑같은 인스턴스를 반환하고 없으면 생성하기 때문에 싱글톤을 보장한다.

<u style="color:#099">만약 @Configuration 붙이지 않고  @Bean 만 사용한다면 프록시 객체 정보가아닌 순수한 정보를 반환하는 것을 알 수 있다.
그리고 getBean 을 통해 memberService, orderService를 부르게 되면 System.out.println("AppConfig_DI.memberRepository"); 출력문이
각 서비스가 생성되면서 찍히는 것을 알 수 있다.</u>


![캡처](https://user-images.githubusercontent.com/67587446/111351049-19d57c00-86c6-11eb-99fb-5e7f8df24c8a.PNG)


### 핵심
    - 스프링의 설정 정보 (의존 관계 주입) 에서는 @Configuration 을 사용하고 이 애노테이션은 싱글톤을 보장해준다
    - 싱글톤 방식에서 statuful 하게 아닌 stateless 하게 설계해야한다.(공유 변수는 함부로 사용해선 안된다)
    - 스프링이 제공하는 방식 Default는 싱글톤 컨테이너 방식이다.
    - 스프링 컨테이너의 동작원리를 이해한다.(DI)



--- ---

# 컴포넌트 스캔
