package zipgo.auth.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public String createToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthMemberResponse oAuthMemberResponse = oAuthClient.getMember(accessToken);

        Optional<Member> memberOptional = memberRepository.findByEmail(oAuthMemberResponse.getEmail());
        if (memberOptional.isPresent()) {
            return jwtProvider.create(String.valueOf(memberOptional.get().getId()));
        }

        Member member = oAuthMemberResponse.toMember();
        Long memberId = memberRepository.save(member).getId();
        return jwtProvider.create(String.valueOf(memberId));
    }

}
