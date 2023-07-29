package zipgo.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipgo.auth.application.AuthService;
import zipgo.auth.presentation.dto.TokenResponse;
import zipgo.member.application.MemberQueryService;
import zipgo.member.domain.Member;
import zipgo.auth.presentation.dto.AuthResponse;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam("code") String authCode) {
        String token = authService.createToken(authCode);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getMemberInfo(@Auth String id) {
        Member member = memberQueryService.findById(id);
        return ResponseEntity.ok(AuthResponse.from(member));
    }

}
