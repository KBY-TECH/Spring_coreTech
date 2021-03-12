package core.core_tech.domain.member;

import core.core_tech.domain.member.Member;
import core.core_tech.domain.member.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component // 빈 자동 등록.
public class memoryMemberRepository implements MemberRepository {
    private static ConcurrentHashMap<Long, Member> store = new ConcurrentHashMap<>(); // 동시성 issue 발생.

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }
}
