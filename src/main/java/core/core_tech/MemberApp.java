package core.core_tech;

import core.core_tech.domain.member.Grade;
import core.core_tech.domain.member.Member;
import core.core_tech.domain.member.service.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {

//        AppConfig appConfig=new AppConfig();
//        MemberService memberService=appConfig.memberService();
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig_DI.class);
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        Member member=new Member(1L,"A", Grade.VIP);
        memberService.Join(member);

        Member find=memberService.findMember(1L);
        System.out.println("find Memeber : "+find.toString());
        System.out.println("Join Memeber : "+member.toString());

    }
}
