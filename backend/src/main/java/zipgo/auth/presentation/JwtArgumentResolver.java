package zipgo.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import zipgo.auth.presentation.dto.AuthDto;
import zipgo.auth.util.JwtProvider;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class)
                && parameter.getParameterType().equals(AuthDto.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = BearerTokenExtractor.extract(Objects.requireNonNull(request));
        String id = jwtProvider.getPayload(token);

        return new AuthDto(id);
    }

}
