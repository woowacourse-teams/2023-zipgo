package zipgo.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.application.AuthService;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("code") String authCode) {
        String token = authService.createToken(authCode);
        return ResponseEntity.ok(token);
    }

}
