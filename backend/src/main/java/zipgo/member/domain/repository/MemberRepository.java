package zipgo.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.member.domain.Member;
import zipgo.member.exception.MemberException;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    default Member getById(Long id) {
        return findById(id)
                .orElseThrow(MemberException.NotFound::new);
    }

}
