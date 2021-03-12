package core.core_tech.Scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class singleTon {
    @Test
    void singleTonTest()
    {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(single.class);
        single test=ac.getBean(single.class);
        single test2=ac.getBean(single.class);
        // 똑같은 인스턴스..

        System.out.println("singleTon.singleTonTest");
        System.out.println(test);
        System.out.println(test2);

        Assertions.assertThat(test).isSameAs(test2);
        Assertions.assertThat(test).isEqualTo(test2);
        ac.close();

    }


    @Scope("singleton")
    static class single{

        @PostConstruct
        public void init(){
            System.out.println("single.init");
        }

        @PreDestroy
        public void close(){
            System.out.println("single.close");
        }
    }
}
