package zipgo.auth.presentation;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.application.AuthServiceFacade;
import zipgo.auth.dto.AccessTokenResponse;
import zipgo.auth.dto.AuthCredentials;
import zipgo.auth.dto.AuthResponse;
import zipgo.auth.dto.LoginResponse;
import zipgo.auth.dto.TokenDto;
import zipgo.auth.support.JwtProvider;
import zipgo.auth.support.ZipgoTokenExtractor;
import zipgo.member.application.MemberQueryService;
import zipgo.member.domain.Member;
import zipgo.pet.application.PetQueryService;
import zipgo.pet.domain.Pet;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthServiceFacade authServiceFacade;
    private final MemberQueryService memberQueryService;
    private final PetQueryService petQueryService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestParam("code") String authCode,
            @RequestParam("redirect-uri") String redirectUri
    ) {
        TokenDto tokenDto = authServiceFacade.login(authCode, redirectUri);

        String memberId = jwtProvider.getPayload(tokenDto.accessToken());
        Member member = memberQueryService.findById(Long.valueOf(memberId));
        List<Pet> pets = petQueryService.readMemberPets(member.getId());

        return ResponseEntity.ok(LoginResponse.of(tokenDto, member, pets));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> renewTokens(HttpServletRequest request) {
        String refreshToken = ZipgoTokenExtractor.extract(request);
        String accessToken = authServiceFacade.renewAccessTokenBy(refreshToken);
        return ResponseEntity.ok(AccessTokenResponse.from(accessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Auth AuthCredentials authCredentials) {
        authServiceFacade.logout(authCredentials.id());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<AuthResponse> getMemberDetail(@Auth AuthCredentials authCredentials) {
        Member member = memberQueryService.findById(authCredentials.id());
        List<Pet> pets = petQueryService.readMemberPets(member.getId());
        return ResponseEntity.ok(AuthResponse.of(member, pets));
    }

}
