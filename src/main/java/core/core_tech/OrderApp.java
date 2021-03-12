package core.core_tech;


import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import core.core_tech.domain.member.service.MemberService;
import core.core_tech.domain.order.service.Order;
import core.core_tech.domain.order.service.service.orderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class OrderApp {
    public static void main(String[] args) {
//        AppConfig appConfig=new AppConfig();
//        MemberService memberService=appConfig.memberService();
//        orderService orderService=appConfig.orderService();
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig_DI.class);
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        orderService orderService=applicationContext.getBean("orderService", orderService.class);
        Long id=1L;
        Member member=new Member(id,"A", Grade.VIP);
        memberService.Join(member);
        Order order=orderService.createOrder(id,"Product",100000);
        System.out.println("order --> "+order);
        System.out.println("해당 회원은 "+order.calculatePrice()+"원에 구매 가능합니다.");
    }
}
