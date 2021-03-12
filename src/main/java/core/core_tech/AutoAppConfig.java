package core.core_tech;

import core.core_tech.domain.member.MemberRepository;
import core.core_tech.domain.member.memoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @ComponentScan 붙은거 전부 빈 등록.
@ComponentScan (excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Configuration.class)
},basePackages = "core.core_tech.domain",basePackageClasses = AutoAppConfig.class) // autoAppConfig class 에 package 서부터 찾음. default 는 여기 패키지 그래서 최상단 루트에 이 클래스는 !
public class AutoAppConfig {

}
