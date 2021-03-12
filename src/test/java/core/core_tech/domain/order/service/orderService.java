package core.core_tech.domain.order.service;

import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import core.core_tech.domain.member.service.MemberService;
import core.core_tech.domain.member.service.MemberServiceImpl;
import core.core_tech.domain.order.service.service.orderServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

//public class orderService {
//
//    MemberService memberService=new MemberServiceImpl();
//    orderService orderService =new orderServiceImpl();
//
//    @Test
//    void createOrder()
//    {
//        Long memberId=1L;
//        Member member=new Member(memberId,"name", Grade.VIP);
//
//        Order order=orderService.createOrder(memberId,"item",10000);
//        Assert.assertEquals(order.getDiscountPrice(),1000);
//    }
//}
