package core.core_tech.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {
    //    private final ObjectProvider<MyLogger> provider;
    private final MyLogger myLogger;

    public void logic(String id) {
//        MyLogger myLogger = provider.getObject();
        myLogger.log("service id -> " + id);
    }
}
