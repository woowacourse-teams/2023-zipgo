package zipgo.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
