package zipgo.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipgo.auth.presentation.dto.LoginRequest;
import zipgo.auth.util.Tokens;
import zipgo.auth.application.AuthService;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody @Valid LoginRequest loginRequest) {
        Tokens tokens = authService.login(loginRequest.getAuthCode());
        return ResponseEntity.ok(tokens);
    }

}
