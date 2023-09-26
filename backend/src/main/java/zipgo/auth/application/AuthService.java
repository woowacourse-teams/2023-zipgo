package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.domain.RefreshToken;
import zipgo.auth.dto.TokenDto;
import zipgo.auth.domain.repository.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto login(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthMemberResponse oAuthMemberResponse = oAuthClient.getMember(accessToken);

        Member member = memberRepository.findByEmail(oAuthMemberResponse.getEmail())
                .orElseGet(() -> memberRepository.save(oAuthMemberResponse.toMember()));

        return createTokens(member.getId());
    }

    private TokenDto createTokens(Long memberId) {
        String accessToken = jwtProvider.createAccessToken(memberId.toString());
        String refreshToken = jwtProvider.createRefreshToken();

        refreshTokenRepository.deleteByMemberId(memberId);
        refreshTokenRepository.save(new RefreshToken(memberId, refreshToken));

        return TokenDto.of(accessToken, refreshToken);
    }

    public TokenDto renewTokens(String token) {
        jwtProvider.validateParseJws(token);

        RefreshToken savedRefreshToken = refreshTokenRepository.getByToken(token);
        Long memberId = savedRefreshToken.getMemberId();

        return createTokens(memberId);
    }

}
