package core.core_tech.domain.member;


public interface MemberRepository {

    void save(Member member);

    Member findById(Long id);
}
