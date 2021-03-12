package core.core_tech.lifeCycle;

import org.junit.platform.engine.support.discovery.EngineDiscoveryRequestResolver;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NetworkClient  {
    private String url;

    public NetworkClient() {
        System.out.println("NetworkClient.NetworkClient");
        System.out.println("url : "+url);
        System.out.println("NetworkClient.NetworkClient Close");
        System.out.println();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect()
    {
        System.out.println("NetworkClient.connect");
        System.out.println("connect url = " + url);
        System.out.println();

    }
    public void call(String msg)
    {
        System.out.println("NetworkClient.call");
        System.out.println("call url = " + url);
        System.out.println("msg = " + msg);
        System.out.println();

    }
    public void disconnect()
    {
        System.out.println("NetworkClient.disconnect");
        System.out.println("close url :"+url);
        System.out.println();

    }

    // 의존관계 주입 후에 호출하겟다.
    @PostConstruct
    public void init() throws Exception {

        System.out.println("Annotation : NetworkClient.init");
        setUrl("www.KBY_TECH.com");
        connect();
        call("초기화 설정");
        System.out.println();

    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("Annotation : NetworkClient.close");
        disconnect();
        System.out.println();

    }
}
