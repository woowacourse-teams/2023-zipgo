package zipgo.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import zipgo.auth.infra.kakao.config.KakaoCredentials;

@Configuration
@EnableConfigurationProperties({
        KakaoCredentials.class,
        JwtCredentials.class,
        AwsS3Credentials.class
})
public class AdditionalConfiguration {

}
