package core.core_tech.domain.discount;

import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class rateDiscountPolicyTest {
    rateDiscountPolicy rateDiscountPolicy=new rateDiscountPolicy()  ;
    @Test
    @DisplayName("VIP는 테스트가 되어야 한다.")
    void vip()
    {
        // given
        Member m=new Member(1L,"member1", Grade.VIP);
        // when
        int discount=rateDiscountPolicy.discount(m,10000);
        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }
    @Test
    @DisplayName("VIP가 아니면 적용이 안되어야 한다.")
    void guest()
    {
        // given
        Member m=new Member(1L,"member1", Grade.BASIC);
        // when
        int discount=rateDiscountPolicy.discount(m,10000);
        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }

}