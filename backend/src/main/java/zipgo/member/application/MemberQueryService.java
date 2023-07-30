package zipgo.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findById(String id) {
        return memberRepository.getById(Long.valueOf(id));
    }

}

