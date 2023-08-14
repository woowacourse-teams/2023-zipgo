package zipgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import zipgo.auth.infra.kakao.config.KakaoCredentials;
import zipgo.common.config.JwtCredentials;

@SpringBootApplication
@EnableConfigurationProperties({KakaoCredentials.class, JwtCredentials.class})
public class ZipgoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipgoApplication.class, args);
    }

}
