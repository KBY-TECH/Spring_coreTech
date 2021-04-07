package core.core_tech.Autowired;

import core.core_tech.AutoAppConfig;
import core.core_tech.domain.discount.discountPolicy;
import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class AllBean {

    @Test
    public void allBean()
    {
        ApplicationContext ac=new AnnotationConfigApplicationContext(AutoAppConfig.class,DiscountService.class);
        DiscountService discountService=ac.getBean(DiscountService.class);
        Member member= Member.builder()
                .id(1L).name("김병연").grade(Grade.VIP)
                .build();
        int discountPrice=discountService.discount(member,10000,"rateDiscountPolicy");
       Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
       Assert.assertEquals(discountPrice,1000);

    }

    static class DiscountService{
        private final Map<String, discountPolicy> policyMap;
        private final List<discountPolicy> list;

        public DiscountService(Map<String, discountPolicy> policyMap, List<discountPolicy> list) {
            this.policyMap = policyMap;
            this.list = list;
            System.out.println("policyMap = " + policyMap.keySet() + "," +policyMap.values() +","+
                    " list = " + list);;
        }
        public int discount(Member member,int price,String discountCode)
        {
            discountPolicy discountPolicy=policyMap.get(discountCode);
            System.out.println("price = " + price + ", discountCode = " + discountCode);

            return discountPolicy.discount(member,price);
        }
    }

}
