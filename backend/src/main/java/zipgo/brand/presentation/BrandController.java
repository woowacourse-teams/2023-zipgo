package zipgo.brand.presentation;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zipgo.brand.application.BrandService;
import zipgo.brand.dto.BrandCreateRequest;
import zipgo.image.ImageDirectoryUrl;
import zipgo.image.application.ImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<Void> createBrand(
            @RequestPart BrandCreateRequest brandCreateRequest,
            @RequestPart MultipartFile image
    ) {
        String imageUrl = imageService.save(image, ImageDirectoryUrl.BRAND_DIRECTORY);
        Long brandId = brandService.createBrand(brandCreateRequest, imageUrl);
        return ResponseEntity.created(URI.create("/brands/" + brandId)).build();
    }

}
