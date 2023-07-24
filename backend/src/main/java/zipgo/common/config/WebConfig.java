package zipgo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpHeaders.LOCATION;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOWED_METHODS = "*";
    private static final String PROD_ORIGIN = "*";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHODS)
                .allowedOrigins(PROD_ORIGIN)
                .exposedHeaders(LOCATION);
    }

}
