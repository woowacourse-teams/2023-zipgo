package zipgo.common.config;

import static org.springframework.http.HttpHeaders.LOCATION;

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

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOW_ALL_PATH = "/**";
    private static final String ALLOWED_METHODS = "*";

    private final AuthInterceptor authInterceptor;
    private final JwtProvider jwtProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALLOW_ALL_PATH)
                .allowedMethods(ALLOWED_METHODS)
                .allowedOrigins("http://localhost:3000", "http://localhost", "http://3.39.240.11")
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
