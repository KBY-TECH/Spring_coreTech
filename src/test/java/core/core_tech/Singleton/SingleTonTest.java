package core.core_tech.Singleton;

import core.core_tech.AppConfig_DI;
import core.core_tech.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingleTonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너 문제점 찾아보기")
    public void singleTest()
    {
        AppConfig_DI appConfig_di = new AppConfig_DI();
        MemberService memberService=appConfig_di.memberService();
        MemberService memberService2=appConfig_di.memberService();
        System.out.println(memberService);
        System.out.println(memberService2);
        Assertions.assertThat(memberService).isNotSameAs(memberService2);
        // result 2개개
       /*
        core.core_tech.domain.member.service.MemberServiceImpl@10683d9d
        core.core_tech.domain.member.service.MemberServiceImpl@3fc2959f
        */
    }

    @Test
    @DisplayName("스프링 있는 싱글톤 패턴으로 하나의 객체를 공유하기")
    public void singleTonexeTest()
    {
        // DEFAUL == SINGLETON .
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig_DI.class);
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        MemberService memberService2=applicationContext.getBean("memberService",MemberService.class);
        System.out.println(memberService);
        System.out.println(memberService2);
        Assertions.assertThat(memberService).isSameAs(memberService2);
        // result 1개로 공유
       /*
      core.core_tech.domain.member.service.MemberServiceImpl@2c5529ab
core.core_tech.domain.member.service.MemberServiceImpl@2c5529ab
        */
    }

    @Test
    @DisplayName("싱글톤 패턴 적용하는 객체 사용")
    void singleTonService()
    {
//        new singletonService();컴파일 오류
        singletonService s1=singletonService.getIns();
        singletonService s2=singletonService.getIns();
        singletonService s3=singletonService.getIns();

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        Assertions.assertThat(s1).isSameAs(s2);
        Assertions.assertThat(s2).isSameAs(s3);
        Assertions.assertThat(s1).isSameAs(s3);


    }
}
