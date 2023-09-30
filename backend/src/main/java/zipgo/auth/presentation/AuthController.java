package zipgo.auth.presentation;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.application.AuthService;
import zipgo.auth.dto.AccessTokenResponse;
import zipgo.auth.dto.AuthCredentials;
import zipgo.auth.dto.AuthResponse;
import zipgo.auth.dto.LoginResponse;
import zipgo.auth.dto.TokenDto;
import zipgo.auth.support.JwtProvider;
import zipgo.auth.support.RefreshTokenCookieProvider;
import zipgo.member.application.MemberQueryService;
import zipgo.member.domain.Member;
import zipgo.pet.application.PetQueryService;
import zipgo.pet.domain.Pet;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static zipgo.auth.support.RefreshTokenCookieProvider.REFRESH_TOKEN;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;
    private final MemberQueryService memberQueryService;
    private final PetQueryService petQueryService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam("code") String authCode) {
        TokenDto tokenDto = authService.login(authCode);
        ResponseCookie cookie = refreshTokenCookieProvider.createCookie(tokenDto.refreshToken());

        String memberId = jwtProvider.getPayload(tokenDto.accessToken());
        Member member = memberQueryService.findById(Long.valueOf(memberId));
        List<Pet> pets = petQueryService.readMemberPets(member.getId());

        return ResponseEntity.ok()
                .header(SET_COOKIE, cookie.toString())
                .body(LoginResponse.of(tokenDto.accessToken(), member, pets));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> renewTokens(@CookieValue(value = REFRESH_TOKEN) String refreshToken) {
        String accessToken = authService.renewAccessTokenBy(refreshToken);
        return ResponseEntity.ok(AccessTokenResponse.from(accessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Auth AuthCredentials authCredentials) {
        authService.logout(authCredentials.id());
        ResponseCookie logoutCookie = refreshTokenCookieProvider.createLogoutCookie();
        return ResponseEntity.ok()
                .header(SET_COOKIE, logoutCookie.toString())
                .build();
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getMemberDetail(@Auth AuthCredentials authCredentials) {
        Member member = memberQueryService.findById(authCredentials.id());
        List<Pet> pets = petQueryService.readMemberPets(member.getId());
        return ResponseEntity.ok(AuthResponse.of(member, pets));
    }

}
