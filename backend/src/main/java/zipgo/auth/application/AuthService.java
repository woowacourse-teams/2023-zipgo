package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.support.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public String createAccessToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthMemberResponse oAuthMemberResponse = oAuthClient.getMember(accessToken);

        Member member = memberRepository.findByEmail(oAuthMemberResponse.getEmail())
                .orElseGet(() -> memberRepository.save(oAuthMemberResponse.toMember()));

        return jwtProvider.createAccessToken(String.valueOf(member.getId()));
    }

    public String createRefreshToken() {
        return jwtProvider.createRefreshToken();
    }

}
