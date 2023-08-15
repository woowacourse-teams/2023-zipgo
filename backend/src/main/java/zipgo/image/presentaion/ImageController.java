package zipgo.image.presentaion;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zipgo.image.application.ImageService;
import zipgo.image.presentaion.dto.ImageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageResponse> upload(@RequestPart MultipartFile file) {
        String url = imageService.save(file);
        return ResponseEntity.created(URI.create(url)).body(ImageResponse.from(url));
    }

}
