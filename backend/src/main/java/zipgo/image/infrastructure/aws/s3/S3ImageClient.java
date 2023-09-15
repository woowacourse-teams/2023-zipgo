package zipgo.image.infrastructure.aws.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import zipgo.common.config.AwsS3Credentials;
import zipgo.image.application.ImageClient;

@Component
@RequiredArgsConstructor
public class S3ImageClient implements ImageClient {

    private final AwsS3Credentials awsS3Credentials;
    private final S3Uploader s3Uploader;

    @Override
    public String upload(String name, MultipartFile file, String directory) {
        String bucket = awsS3Credentials.getBucket();
        String zipgoDirectoryName = awsS3Credentials.getZipgoDirectoryName();
        String key = zipgoDirectoryName + directory + name;
        s3Uploader.upload(bucket, key, file);
        return awsS3Credentials.getImageUrl() + directory + name;
    }

}
