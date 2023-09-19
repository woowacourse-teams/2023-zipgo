package zipgo.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class AwsS3Credentials {

    private final String bucket;
    private final String zipgoDirectory;
    private final String environment;
    private final String imageUrl;

}
