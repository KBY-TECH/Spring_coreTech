package core.core_tech.Singleton;

import core.core_tech.AppConfig_DI;
import core.core_tech.domain.member.MemberRepository;
import core.core_tech.domain.member.service.MemberServiceImpl;
import core.core_tech.domain.order.service.service.orderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class statefulServiceTest {

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

    static class Testconfig {
        @Bean
        public statefulService statefulService() {
            return new statefulService();
        }
    }

    @Test
    @DisplayName("appconfig 에서 memeberSeriviceImpl 이랑 orderServiceImpl 에 있는 memberRepository는 싱글톤을 유지할까? 서로 다른 new로 생성 되는데?")
    public void test() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig_DI.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        orderServiceImpl orderService = ac.getBean("orderService",
                orderServiceImpl.class);
        MemberRepository memberRepository=ac.getBean("memberRepository",MemberRepository.class);
            // 구현 객체를 직접 꺼내면 안됨 단순 싱글톤 테스트임.
        System.out.println("statefulServiceTest.test");
        System.out.println(memberService.getMemberRepository());
        System.out.println(orderService.getMemberRepository());
        System.out.println(memberRepository);

        /*
        ccore.core_tech.domain.member.memoryMemberRepository@2dd80673
        core.core_tech.domain.member.memoryMemberRepository@2dd80673
        core.core_tech.domain.member.memoryMemberRepository@2dd80673
        */
        assertThat(memberService.getMemberRepository()).isSameAs(orderService.getMemberRepository());
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);

    }
    @Test
    void configuration()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig_DI.class);

        AppConfig_DI bean=ac.getBean(AppConfig_DI.class);

        System.out.println("statefulServiceTest.configuration");
        System.out.println("bean is "+bean.getClass()); //$$EnhancerBySpringCGLIB$$41169ffc

    }
}