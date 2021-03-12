package core.core_tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 여 안에 이미 componentScan 이 있으며 이클래스가 최상위 루트에 있음.
public class CoreTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreTechApplication.class, args);
    }

}
