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
        System.out.println("[uuid : "+this.uuid+"] [requestUrl : "+this.requestUrl+"]  [msg : "+msg);
    }
    @PostConstruct
    public void init()
    {
        uuid= UUID.randomUUID().toString();
        System.out.println("CREATE  [MyLogger.init refAddress : "+this+" ] [ uuid :"+uuid+" ]");
    }

    @PreDestroy
    public void close()
    {
        System.out.println("\n CLOSEl  [ uuid : "+uuid+" ] \n");
    }
}
