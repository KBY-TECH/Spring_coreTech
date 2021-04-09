package core.core_tech.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LogDemoController {
//    private final ObjectProvider<MyLogger> provider;
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    @RequestMapping("log-demo")
    @ResponseBody
    public String lognerApiTest(HttpServletRequest request) throws InterruptedException {
        String s = request.getRequestURL().toString();
//        System.out.println("proxy -----> "+myLogger.getClass());
//        MyLogger myLogger=provider.getObject();
        myLogger.setRequestUrl(s);
        Thread.sleep(1000);
        myLogger.log("this is controller");
        logDemoService.logic("testId!");
        return "return Result";
    }
}
