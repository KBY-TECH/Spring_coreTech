package core.core_tech.Scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class protoType {
    @Test
    void prototypeTest()
    {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(prototype.class);
        prototype p1=ac.getBean(prototype.class);
        prototype p2=ac.getBean(prototype.class);
//        System.out.println("p1 = " + p1);
//        System.out.println("p2 = " + p2);
        Assertions.assertThat(p1).isNotEqualTo(p2);
        Assertions.assertThat(p1).isNotSameAs(p2);
        System.out.println("protoType.prototypeTest");
        // 수동으로 종료해야줘야 함.
        p1.close();
        p2.close();
        ac.close(); // 반응 안함.
    }

    @Scope("prototype")
    static public class prototype{
         static public int count=0;
        @PostConstruct
        public void init()
        {
            System.out.println("prototype.init");
            count++;
        }

        @PreDestroy
        public void close()
        {
            System.out.println("prototype.close");
        }
    }
}
