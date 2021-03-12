package core.core_tech.domain.discount;

import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements discountPolicy {
    private final int discount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discount;
        }
        return 0;
    }
}
