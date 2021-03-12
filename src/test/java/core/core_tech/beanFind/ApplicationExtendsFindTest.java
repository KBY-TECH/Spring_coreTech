package core.core_tech.beanFind;

import core.core_tech.AppConfig_DI;
import core.core_tech.domain.discount.FixDiscountPolicy;
import core.core_tech.domain.discount.discountPolicy;
import core.core_tech.domain.discount.rateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.BeanProperty;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Testcon.class);

    @Configuration
    static class Testcon {
        @Bean
        public discountPolicy rate() {
            return new rateDiscountPolicy();
        }

        @Bean
        public discountPolicy fix() {
            return new FixDiscountPolicy();
        }
    }

    @Test
    @DisplayName("부모 조회시 자식이 둘이상이면 오류..")
    void findBeanByParentTypeDuplicate() {
//        ac.getBean(discountPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class,()->ac.getBean(discountPolicy.class));
    }

    @Test
    @DisplayName("오류 안나게 꺼내기 자식이 2이상일떄. (타입과 이름으로 조회)")
    void findBeanByParentTypeDuplicate2() {
        discountPolicy fix = ac.getBean("fix", discountPolicy.class);
        assertThat(fix).isInstanceOf(FixDiscountPolicy.class);
    }

    @Test
    @DisplayName("하위 타입 타입으로 조회")
    void FindBeanBySubType()
    {
        rateDiscountPolicy bean = ac.getBean(rateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(rateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 맵")
    void findAllParentType()
    {
        Map<String, discountPolicy> beansOfType = ac.getBeansOfType(discountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String s : beansOfType.keySet()) {
            System.out.println("key : "+s+" val :"+beansOfType.get(s));
        }
    }
    @Test
    @DisplayName("부모 타입으로 obeject 로 모두 꺼내기")
    void findAllObjectType()
    {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String s : beansOfType.keySet()) {
            System.out.println("key : "+s+"      val :"+beansOfType.get(s));
        }
    }

}
