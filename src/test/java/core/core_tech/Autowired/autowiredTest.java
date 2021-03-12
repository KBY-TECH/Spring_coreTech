package core.core_tech.Autowired;

import core.core_tech.domain.member.Member;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Nullable;
import java.util.Optional;

public class autowiredTest {

    @Test
    @DisplayName("순수 테스트")
    public void autowiredOptionTest()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        ac.getBean(TestBean.class);
    }

    static class TestBean{

        @Autowired(required = false)
        public void noBean(Member member)
        {
            System.out.println("noBean : "+member);
        }

        @Autowired
        public void noBean2(@Nullable Member member)
        {
            System.out.println("noBean2 : "+member);
        }
        @Autowired(required = false)
        public void noBean3(Optional<Member> member)
        {
            System.out.println("noBean3 : "+member);
        }
    }
}