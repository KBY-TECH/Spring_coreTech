package core.core_tech.domain.member.service;

import core.core_tech.domain.member.Member;

public interface MemberService {
    // 회원 가입
    void Join(Member member);

    // 조회 (id를 통한 조회)
    Member findMember(Long memberId);
}
