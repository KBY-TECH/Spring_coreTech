package core.core_tech.beanFind;

import core.core_tech.AppConfig_DI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig_DI.class);

    @Test
    @DisplayName("모든 빈 찾기")
    void findBean()
    {
        String[] beanNames=ac.getBeanDefinitionNames();
        for(String a:beanNames)
        {
            Object bean=ac.getBean(a);
            System.out.println("name :"+a+" ---> object : "+bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 찾기")
    void findApplicationBean()
    {
        String[] beanNames=ac.getBeanDefinitionNames();
        for(String a:beanNames)
        {
            BeanDefinition beanDefinition=ac.getBeanDefinition((a));
            if(beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION)
            {
                Object bean=ac.getBean(a);
                System.out.println("name :"+a+" ---> object : "+bean);
            }
        }
    }

}
