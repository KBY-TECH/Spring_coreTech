package core.core_tech.beanFind;

import core.core_tech.AppConfig_DI;
import core.core_tech.domain.member.service.MemberService;
import core.core_tech.domain.member.service.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.assertj.core.api.Assertions.*;
public class ApplicationContextBasicBean {
    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig_DI.class);

    @Test
    @DisplayName("이름과 타입으로 조회")
    void findBytNameBean()
    {
        MemberService memberService=ac.getBean("memberService",MemberService.class);
        System.out.println("memberService :"+memberService);
        System.out.println("memberService.getClass() :"+memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("애플리케이션 빈 타입으로만 찾기")
    void findBytTypeBean()
    {
        MemberService memberService=ac.getBean(MemberService.class);
        System.out.println("memberService :"+memberService);
        System.out.println("memberService.getClass() :"+memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회 --> 구현부분에 의존") // 유연성 오류
    void findBySpecTypeBean()
    {
        MemberService memberService=ac.getBean("memberService",MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름으로 조회시 실패 코드")
    void findByNameBean_F()
    {
//        MemberService memberServi=ac.getBean("xxxx",MemberService.class);
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,()-> ac.getBean("xxxx",MemberService.class));
    }
}
