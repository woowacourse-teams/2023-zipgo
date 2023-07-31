package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.application.dto.OAuthResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public String createToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthResponse oAuthResponse = oAuthClient.getMemberDetail(accessToken);

        Optional<Member> memberOptional = memberRepository.findByEmail(oAuthResponse.getEmail());
        if (memberOptional.isPresent()) {
            return jwtProvider.create(String.valueOf(memberOptional.get().getId()));
        }

        Member member = oAuthResponse.toMember();
        Long memberId = memberRepository.save(member).getId();
        return jwtProvider.create(String.valueOf(memberId));
    }

}
