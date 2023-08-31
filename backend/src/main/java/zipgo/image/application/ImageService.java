package zipgo.image.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageClient imageClient;

    public String save(MultipartFile image, String directory) {
        UUID uuid = UUID.randomUUID();
        return imageClient.upload(uuid.toString(), image, directory);
    }

}
