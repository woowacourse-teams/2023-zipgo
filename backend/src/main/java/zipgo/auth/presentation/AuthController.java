package zipgo.auth.presentation;

import java.util.List;
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
import zipgo.pet.application.PetQueryService;
import zipgo.pet.domain.Pet;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final MemberQueryService memberQueryService;
    private final PetQueryService petQueryService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam("code") String authCode) {
        String token = authService.createToken(authCode);
        String memberId = jwtProvider.getPayload(token);
        Member member = memberQueryService.findById(Long.valueOf(memberId));
        List<Pet> pets = petQueryService.readMemberPets(member.getId());
        return ResponseEntity.ok(TokenResponse.of(token, member, pets));
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getMemberDetail(@Auth AuthCredentials authCredentials) {
        Member member = memberQueryService.findById(authCredentials.id());
        List<Pet> pets = petQueryService.readMemberPets(member.getId());
        return ResponseEntity.ok(AuthResponse.of(member, pets));
    }

}
