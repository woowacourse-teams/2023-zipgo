package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipgo.auth.OAuthResponse;
import zipgo.auth.util.Tokens;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final MemberRepository memberRepository;

    public Tokens login(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthResponse memberDetail = oAuthClient.getMemberDetail(accessToken);

        Long memberId = getMemberId(memberDetail);
        return new Tokens(null, null);
    }

    private Long getMemberId(OAuthResponse oAuthResponse) {
        return memberRepository.findByEmail(oAuthResponse.getEmail())
                .map(Member::getId)
                .orElse(generateMember(oAuthResponse));
    }

    private Long generateMember(OAuthResponse oAuthResponse) {
        Member member = Member.builder()
                .name(oAuthResponse.getName())
                .email(oAuthResponse.getEmail())
                .build();

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

}
