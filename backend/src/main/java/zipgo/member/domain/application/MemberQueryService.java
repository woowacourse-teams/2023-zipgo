package zipgo.member.domain.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member save(MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.email())
                .profileImgUrl(memberDto.profileImgUrl())
                .name(memberDto.name())
                .build();
        return memberRepository.save(member);
    }

}
