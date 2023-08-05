package zipgo.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zipgo.auth.presentation.AuthInterceptor;
import zipgo.auth.presentation.JwtArgumentResolver;
import zipgo.auth.util.JwtProvider;

import static org.springframework.http.HttpHeaders.LOCATION;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOW_ALL_PATH = "/**";
    private static final String ALLOWED_METHODS = "*";
    private static final String MAIN_SERVER_DOMAIN = "https://zipgo.pet";
    private static final String MAIN_SERVER_WWW_DOMAIN = "https://www.zipgo.pet";
    private static final String FRONTEND_LOCALHOST = "http://localhost:3000";

    private final AuthInterceptor authInterceptor;
    private final JwtProvider jwtProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALLOW_ALL_PATH)
                .allowedMethods(ALLOWED_METHODS)
                .allowedOrigins(MAIN_SERVER_DOMAIN, MAIN_SERVER_WWW_DOMAIN, FRONTEND_LOCALHOST)
                .exposedHeaders(LOCATION);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/auth");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtArgumentResolver(jwtProvider));
    }

}
