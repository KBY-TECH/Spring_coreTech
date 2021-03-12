package core.core_tech.Scan;

import core.core_tech.AutoAppConfig;
import core.core_tech.domain.member.service.MemberService;
import core.core_tech.domain.member.service.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class AutoAppConfigTest {

    @Test
    @DisplayName("스캔")
    public void basicScan()
    {
        ApplicationContext ac=new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService=ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
//        Assert.assertEquals(memberService,MemberService.class);
    }

    @Test
    @DisplayName("Filter Test")
    public void filterTest()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(CompenentFiter.class);
        beanA a=ac.getBean("beanA",beanA.class);
        Assert.assertNotNull(a);
//        beanB b=ac.getBean("beanB",beanB.class);
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,() -> ac.getBean("beanB",beanB.class));
    }
    @Configuration
    @ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = IncludeComponent.class)
            ,excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = excludeComponent.class)
    )
    static class CompenentFiter {

    }
}

@IncludeComponent
class beanA
{

}

@excludeComponent
class beanB{

}
