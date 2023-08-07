package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.support.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public String createToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthMemberResponse oAuthMemberResponse = oAuthClient.getMember(accessToken);

        Member member = memberRepository.findByEmail(oAuthMemberResponse.getEmail())
                .orElseGet(() -> memberRepository.save(oAuthMemberResponse.toMember()));

        return jwtProvider.create(String.valueOf(member.getId()));
    }

}
