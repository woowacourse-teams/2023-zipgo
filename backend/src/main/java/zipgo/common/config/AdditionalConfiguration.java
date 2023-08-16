package zipgo.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import zipgo.auth.infra.kakao.config.KakaoCredentials;

@Configuration
@EnableConfigurationProperties({KakaoCredentials.class, JwtCredentials.class, AwsS3Credentials.class})
public class AdditionalConfiguration {

    @Autowired
    private KakaoCredentials kakaoCredentials;

    @Autowired
    private JwtCredentials jwtCredentials;

    @Autowired
    private AwsS3Credentials awsS3Credentials;

}
