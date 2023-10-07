package zipgo.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.dto.TokenDto;

@Service
@RequiredArgsConstructor
public class AuthServiceFacade {

    private final AuthService authService;
    private final OAuthClient oAuthClient;

    public TokenDto login(String authCode) {
        String accessToken = oAuthClient.getAccessToken(authCode);
        OAuthMemberResponse oAuthMemberResponse = oAuthClient.getMember(accessToken);
        return authService.login(oAuthMemberResponse);
    }

    public String renewAccessTokenBy(String refreshToken) {
        return authService.renewAccessTokenBy(refreshToken);
    }

    public void logout(Long memberId) {
        authService.logout(memberId);
    }

}
