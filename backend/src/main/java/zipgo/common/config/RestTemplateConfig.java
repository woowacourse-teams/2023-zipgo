package zipgo.common.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final int TIMEOUT_SECONDS = 2;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .setReadTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
    }

}
