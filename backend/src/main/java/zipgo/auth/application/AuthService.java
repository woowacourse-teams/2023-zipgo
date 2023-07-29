package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.OAuthResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.application.MemberQueryService;
import zipgo.member.application.MemberCommandService;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public String createToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthResponse oAuthResponse = oAuthClient.getMemberDetail(accessToken);

        Optional<Member> memberOptional = memberQueryService.findByEmail(oAuthResponse.getEmail());
        if (memberOptional.isPresent()) {
            return jwtProvider.create(String.valueOf(memberOptional.get().getId()));
        }

        Member member = Member.from(oAuthResponse);
        Long memberId = memberCommandService.save(member).getId();
        return jwtProvider.create(String.valueOf(memberId));
    }

}
