package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.OAuthResponse;
import zipgo.auth.util.JwtTokenProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.application.MemberCommandService;
import zipgo.member.domain.application.MemberQueryService;
import zipgo.member.domain.repository.MemberRepository;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClient oAuthClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public String createToken(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthResponse memberDetail = oAuthClient.getMemberDetail(accessToken);

        Optional<Member> memberOptional = memberCommandService.findByEmail(memberDetail.getEmail());
        if (memberOptional.isPresent()) {
            return jwtTokenProvider.create(String.valueOf(memberOptional.get().getId()));
        }

        Member member = new Member(memberDetail.getNickName(), memberDetail.getEmail());
        Long memberId = memberQueryService.save(member).getId();
        return jwtTokenProvider.create(String.valueOf(memberId));
    }

}

