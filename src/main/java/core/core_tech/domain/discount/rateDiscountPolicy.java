package core.core_tech.domain.discount;

import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component // 빈 자동 등록.
public class rateDiscountPolicy implements discountPolicy{
    private final double discountPercentage=0.1;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP)
            return (int)(price*discountPercentage);
        return 0;
    }
}
