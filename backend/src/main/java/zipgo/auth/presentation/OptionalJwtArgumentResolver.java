package zipgo.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import zipgo.auth.presentation.dto.AuthCredentials;
import zipgo.auth.support.BearerTokenExtractor;
import zipgo.auth.support.JwtProvider;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class OptionalJwtArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OptionalAuth.class)
                && parameter.getParameterType().equals(AuthCredentials.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request.getHeader(AUTHORIZATION) == null) {
            return null;
        }
        String token = BearerTokenExtractor.extract(Objects.requireNonNull(request));
        String id = jwtProvider.getPayload(token);

        return new AuthCredentials(Long.valueOf(id));
    }

}
