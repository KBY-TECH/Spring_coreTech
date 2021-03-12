package core.core_tech.domain.discount;

import core.core_tech.domain.member.Member;

public interface discountPolicy {

    // return 할일 대상 금액.
    int discount(Member member,int price);



}
