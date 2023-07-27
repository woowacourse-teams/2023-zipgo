package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.OAuthResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public String createToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthResponse memberDetail = oAuthClient.getMemberDetail(accessToken);

        Optional<Member> memberOptional = memberRepository.findByEmail(memberDetail.getEmail());
        if (memberOptional.isPresent()) {
            return jwtProvider.create(String.valueOf(memberOptional.get().getId()));
        }

        Member member = new Member(memberDetail.getNickName(), memberDetail.getEmail());
        Long memberId = memberRepository.save(member).getId();
        return jwtProvider.create(String.valueOf(memberId));
    }

}

