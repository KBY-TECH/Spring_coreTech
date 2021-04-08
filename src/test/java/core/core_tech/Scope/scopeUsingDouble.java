package core.core_tech.Scope;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

public class scopeUsingDouble {
    @Test
    void protoTest()
    {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(prototypeTest.class);
        prototypeTest p1=ac.getBean(prototypeTest.class);
        p1.addCount();
        Assertions.assertThat(p1.getCount()).isEqualTo(1);

        prototypeTest p2=ac.getBean(prototypeTest.class);
        p2.addCount();
        Assertions.assertThat(p2.count).isEqualTo(1);
    }

    @Test
    void singleTonClientPrototype(){
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(prototypeTest.class,ClientBean.class);
        ClientBean bean1=ac.getBean(ClientBean.class);
        int cnt1=bean1.logic();
        Assertions.assertThat(cnt1).isEqualTo(1);
        ClientBean bean2=ac.getBean(ClientBean.class);
        int cnt2=bean2.logic();
        Assertions.assertThat(cnt2).isEqualTo(1);
    }

    @Scope("prototype")
    static public class prototypeTest{
        public int count=0;
        public void addCount()
        {
            count++;
        }

        public int getCount() {
            return count;
        }
        @PostConstruct
        public void init()
        {
            System.out.println("prototype.init -> calling : "+this);
        }

        @PreDestroy
        public void close()
        {
            System.out.println("prototype.close");
        }
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean{
//           private final prototypeTest prototypeTest; // 생성 시점에 주입되므로 계속 살아있음.
        // ObjectProvider 또는 ObjectFactory 둘다 사용 가능
        private final ObjectProvider<prototypeTest> prototypeTests;
        private final ObjectFactory<prototypeTest> prototypeTests2;
        private final Provider<prototypeTest> prototypeTestProvider;
//        @Autowired
//        ApplicationContext ac; // 무식하게 주입받기
        @PostConstruct
        public void init(){
            System.out.println("single.init");
        }

        public int logic()
        {
//            prototypeTest prototypeTest= ac.getBean(prototypeTest.class);
//            prototypeTest prototypeTest = prototypeTests.getObject();
            prototypeTest prototypeTest=prototypeTestProvider.get();
            prototypeTest.addCount();
            return prototypeTest.getCount();
        }
        @PreDestroy
        public void close(){
            System.out.println("single.close");
        }
    }
}
