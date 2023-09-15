package zipgo.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.member.domain.Member;
import zipgo.member.exception.MemberNotFoundException;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    default Member getById(Long memberId) {
        return findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

}
