package zipgo.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import zipgo.auth.support.BearerTokenExtractor;
import zipgo.auth.support.JwtProvider;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String accessToken = BearerTokenExtractor.extract(request);
        jwtProvider.validateParseJws(accessToken);
        return true;
    }

}
