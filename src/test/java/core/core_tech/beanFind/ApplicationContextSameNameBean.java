package core.core_tech.beanFind;

import core.core_tech.AppConfig_DI;
import core.core_tech.domain.discount.discountPolicy;
import core.core_tech.domain.member.MemberRepository;
import core.core_tech.domain.member.memoryMemberRepository;
import core.core_tech.domain.member.service.MemberService;
import core.core_tech.domain.member.service.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextSameNameBean {
    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상이면 중복 오류 발생")
    void typeofSearch() {
//        ac.getBean(MemberRepository.class);
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Configuration
    static class SameBeanConfig{
        @Bean
        public MemberRepository memberRepository1()
        {
            return new memoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository2()
        {
            return new memoryMemberRepository();
        }
    }
    @Test
    @DisplayName("타입 중복시 이름도 지정 해준다.")
    void typeSearchBaan()
    {
        MemberRepository memberRepository=ac.getBean("memberRepository1",MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(memoryMemberRepository.class);
    }
    @Test
    @DisplayName("특정 타입 모두 조회하기 ")
    void findall()
    {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String s : beansOfType.keySet()) {
            System.out.println( s+"/"+beansOfType.get(s));
        }
        System.out.println(beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }
}
