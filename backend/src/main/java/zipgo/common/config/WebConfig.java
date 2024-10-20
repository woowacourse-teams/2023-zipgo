package zipgo.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zipgo.auth.presentation.AuthInterceptor;
import zipgo.auth.presentation.JwtMandatoryArgumentResolver;
import zipgo.auth.presentation.OptionalJwtArgumentResolver;
import zipgo.auth.support.JwtProvider;
import zipgo.common.interceptor.LoggingInterceptor;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOW_ALL_PATH = "/**";
    private static final String ALLOWED_METHODS = "*";
    private static final String MAIN_SERVER_DOMAIN = "https://zipgo.pet";
    private static final String DEV_SERVER_DOMAIN = "https://dev.zipgo.pet";
    private static final String FRONTEND_LOCALHOST = "http://localhost:3000";
    private static final String HTTPS_FRONTEND_LOCALHOST = "https://localhost:3000";
    private static final String API_SERVER_DOMAIN = "https://api.zipgo.pet";

    private final AuthInterceptor authInterceptor;
    private final LoggingInterceptor loggingInterceptor;
    private final JwtProvider jwtProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALLOW_ALL_PATH)
                .allowedMethods(ALLOWED_METHODS)
                .allowedOrigins(
                        MAIN_SERVER_DOMAIN,
                        DEV_SERVER_DOMAIN,
                        FRONTEND_LOCALHOST,
                        HTTPS_FRONTEND_LOCALHOST,
                        API_SERVER_DOMAIN
                )
                .allowCredentials(true)
                .exposedHeaders(LOCATION, SET_COOKIE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/auth");

        registry.addInterceptor(loggingInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtMandatoryArgumentResolver(jwtProvider));
        resolvers.add(new OptionalJwtArgumentResolver(jwtProvider));
    }

}
