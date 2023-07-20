package zipgo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpHeaders.LOCATION;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOWED_METHOD_NAMES = "GET,POST,PUT,DELETE,PATCH";
    private static final String DELIMITER = ",";
    private static final String PROD_ORIGIN = "*";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(PROD_ORIGIN)
                .allowedMethods(ALLOWED_METHOD_NAMES.split(DELIMITER))
                .exposedHeaders(LOCATION);
    }

}
