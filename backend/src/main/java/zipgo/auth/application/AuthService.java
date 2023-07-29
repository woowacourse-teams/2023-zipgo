package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.OAuthResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.application.MemberCommandService;
import zipgo.member.domain.application.MemberQueryService;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public String createToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthResponse oAuthResponse = oAuthClient.getMemberDetail(accessToken);

        Optional<Member> memberOptional = memberCommandService.findByEmail(oAuthResponse.getEmail());
        if (memberOptional.isPresent()) {
            return jwtProvider.create(String.valueOf(memberOptional.get().getId()));
        }

        Member member = Member.from(oAuthResponse);
        Long memberId = memberQueryService.save(member).getId();
        return jwtProvider.create(String.valueOf(memberId));
    }

}
