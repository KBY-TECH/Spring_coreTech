package core.core_tech;

import core.core_tech.domain.discount.discountPolicy;
import core.core_tech.domain.discount.rateDiscountPolicy;
import core.core_tech.domain.member.MemberRepository;
import core.core_tech.domain.member.memoryMemberRepository;
import core.core_tech.domain.member.service.MemberService;
import core.core_tech.domain.member.service.MemberServiceImpl;
import core.core_tech.domain.order.service.service.orderService;
import core.core_tech.domain.order.service.service.orderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
