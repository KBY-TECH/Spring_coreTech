package core.core_tech.domain.member.service;

import core.core_tech.AppConfig_DI;
import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberService memberService;
    @BeforeEach
    public void before(){
        AppConfig_DI appConfigDI =new AppConfig_DI();
        memberService= appConfigDI.memberService();
    }

    @Test
    void Join()
    {
        //given
        Member member=new Member(1L,"KBY", Grade.VIP);
        //when
        memberService.Join(member);;
        //then
        System.out.println(memberService.findMember(1L).toString());
    }

}