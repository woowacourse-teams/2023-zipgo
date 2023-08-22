package zipgo.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.application.AuthService;
import zipgo.auth.presentation.dto.AuthCredentials;
import zipgo.auth.presentation.dto.AuthResponse;
import zipgo.auth.presentation.dto.TokenResponse;
import zipgo.auth.support.JwtProvider;
import zipgo.member.application.MemberQueryService;
import zipgo.member.domain.Member;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final MemberQueryService memberQueryService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam("code") String authCode) {
        String token = authService.createToken(authCode);
        String memberId = jwtProvider.getPayload(token);
        Member member = memberQueryService.findById(Long.valueOf(memberId));
        return ResponseEntity.ok(TokenResponse.of(token, member));
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getMemberDetail(@Auth AuthCredentials authCredentials) {
        Member member = memberQueryService.findById(authCredentials.id());
        return ResponseEntity.ok(AuthResponse.from(member));
    }

}
