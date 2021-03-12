package core.core_tech.domain.order.service.service;

import core.core_tech.domain.discount.discountPolicy;
import core.core_tech.domain.member.Member;
import core.core_tech.domain.member.MemberRepository;
import core.core_tech.domain.order.service.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class orderServiceImpl implements orderService {
//    private final MemberRepository memberRepository = new memoryMemberRepository();
//    private final discountPolicy discountPolicy = new FixDiscountPolicy();
//    private final discountPolicy discountPolicy = new rateDiscountPolicy();   => 구체클래스들에게 의존 OCP위반.
    private final MemberRepository memberRepository;
    private final discountPolicy discountPolicy; // 구체에 의존하지 않고 인터페이스만 의존하게 함..

    @Autowired
    public orderServiceImpl(MemberRepository memberRepository, discountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discount = discountPolicy.discount(member, itemPrice); // 당일 책인 원칙.
        return new Order(memberId,itemName,itemPrice,discount);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
