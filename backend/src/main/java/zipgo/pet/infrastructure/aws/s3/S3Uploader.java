package zipgo.pet.infrastructure.aws.s3;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    public void upload(String bucket, String key, MultipartFile file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType("image/png")
                .build();

        try {
            s3Client.putObject(objectRequest, RequestBody.fromBytes(getBytes(file)));
        } catch (UnsupportedOperationException e) {
            throw new IllegalArgumentException("엑세스 접근 중 예외가 발생했습니다.");
        }
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("바이트 파싱 중 에러가 발생했습니다.");
        }
    }

}
