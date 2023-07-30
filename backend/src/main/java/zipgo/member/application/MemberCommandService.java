package zipgo.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

}
