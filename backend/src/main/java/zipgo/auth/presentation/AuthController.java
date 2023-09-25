package zipgo.auth.presentation;

import java.util.List;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.application.AuthService;
import zipgo.auth.dto.AuthCredentials;
import zipgo.auth.dto.AuthResponse;
import zipgo.auth.dto.TokenResponse;
import zipgo.auth.support.JwtProvider;
import zipgo.member.application.MemberQueryService;
import zipgo.member.domain.Member;
import zipgo.pet.application.PetQueryService;
import zipgo.pet.domain.Pet;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String REFRESH_TOKEN = "refreshToken";


    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final MemberQueryService memberQueryService;
    private final PetQueryService petQueryService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestParam("code") String authCode,
            HttpServletResponse response
    ) {
        String accessToken = authService.createAccessToken(authCode);
        setRefreshTokenCookie(response);

        String memberId = jwtProvider.getPayload(accessToken);
        Member member = memberQueryService.findById(Long.valueOf(memberId));
        List<Pet> pets = petQueryService.readMemberPets(member.getId());
        return ResponseEntity.ok(TokenResponse.of(accessToken, member, pets));

    }

    private void setRefreshTokenCookie(HttpServletResponse response) {
        String refreshToken = authService.createRefreshToken();
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN, refreshToken);

        refreshTokenCookie.setMaxAge(99999999);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(refreshTokenCookie);
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getMemberDetail(@Auth AuthCredentials authCredentials) {
        Member member = memberQueryService.findById(authCredentials.id());
        List<Pet> pets = petQueryService.readMemberPets(member.getId());
        return ResponseEntity.ok(AuthResponse.of(member, pets));
    }

}
