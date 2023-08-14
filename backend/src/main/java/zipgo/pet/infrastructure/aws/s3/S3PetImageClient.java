package zipgo.pet.infrastructure.aws.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import zipgo.common.config.AwsS3Credentials;
import zipgo.pet.application.ImageClient;

@Component
@RequiredArgsConstructor
public class S3PetImageClient implements ImageClient {

    private final AwsS3Credentials awsS3Credentials;
    private final S3Uploader s3Uploader;

    @Override
    public String upload(String name, MultipartFile file) {
        String bucket = awsS3Credentials.getBucket();
        String zipgoDirectoryName = awsS3Credentials.getZipgoDirectoryName();
        String petImageDirectory = awsS3Credentials.getPetImageDirectory();
        String key = zipgoDirectoryName + petImageDirectory + name;
        s3Uploader.upload(bucket, key, file);
        return awsS3Credentials.getImageUrl() + petImageDirectory + name;
    }

}
