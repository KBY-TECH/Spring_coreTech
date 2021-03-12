package core.core_tech.web;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS) //ObjectProvider 를 안쓰고 바로 의존성 주입받고 사용하는 방식. scope가 request 임에도 불구하고,
public class MyLogger {

    private String uuid;
    private String requestUrl;

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void log(String msg)
    {
        System.out.println("MyLogger.log");
        System.out.println("uuid : "+this.uuid);
        System.out.println("requestUrl : "+this.requestUrl);
        System.out.println("msg : "+msg);
    }
    @PostConstruct
    public void init()
    {
        uuid= UUID.randomUUID().toString();
        System.out.println("MyLogger.init refAddress -> "+this);
        System.out.println("uuid = " + uuid);
    }

    @PreDestroy
    public void close()
    {
        System.out.println();
        System.out.println("MyLogger.close ");
        System.out.println("uuid = " + uuid+"  referAddress close : "+this) ;
    }
}
