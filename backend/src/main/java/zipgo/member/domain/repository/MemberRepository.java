package zipgo.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.member.domain.Member;
import zipgo.member.exception.MemberException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    default Member getById(Long id) {
        return findById(id).orElseThrow(MemberException.NotFound::new);
    }

}
