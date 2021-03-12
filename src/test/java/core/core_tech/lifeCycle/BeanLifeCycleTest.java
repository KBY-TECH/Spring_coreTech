package core.core_tech.lifeCycle;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void cycleTest()
    {
        ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConnectionPool.class);
        NetworkClient networkClient=applicationContext.getBean(NetworkClient.class);
        applicationContext.close();
    }

    @Configuration
    @ComponentScan
    static class ConnectionPool{

//        @Bean(initMethod = "init",destroyMethod = "close")
//        @Bean
//        public NetworkClient networkClient()
//        {
//            NetworkClient networkClient=new NetworkClient() ;
//            System.out.print("setting value :");
//            networkClient.setUrl("http:dbConnection.dev");
//            System.out.println();
//            return networkClient;
//        }
    }
}
