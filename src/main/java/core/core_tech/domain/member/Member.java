package core.core_tech.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Member{
    private Long id;
    private String name;
    private Grade grade;

    @Builder
    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }



}