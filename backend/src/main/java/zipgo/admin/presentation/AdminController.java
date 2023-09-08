package zipgo.admin.presentation;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import zipgo.admin.application.AdminQueryService;
import zipgo.admin.application.AdminService;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.admin.dto.BrandSelectResponse;
import zipgo.image.ImageDirectoryUrl;
import zipgo.image.application.ImageService;
import zipgo.admin.dto.FunctionalityCreateRequest;
import zipgo.admin.dto.FunctionalitySelectResponse;
import zipgo.admin.dto.PrimaryIngredientCreateRequest;
import zipgo.admin.dto.PrimaryIngredientSelectResponse;
import zipgo.admin.dto.PetFoodCreateRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminQueryService adminQueryService;
    private final AdminService adminService;
    private final ImageService imageService;

    @GetMapping
    String home() {
        return "admin/home";
    }

    @PostMapping("/brands")
    public ResponseEntity<Void> createBrand(
            @RequestPart BrandCreateRequest brandCreateRequest,
            @RequestPart MultipartFile image
    ) {
        String imageUrl = imageService.save(image, ImageDirectoryUrl.BRAND_DIRECTORY);
        Long brandId = adminService.createBrand(brandCreateRequest, imageUrl);
        return ResponseEntity.created(URI.create("/brands/" + brandId)).build();
    }

    @ResponseBody
    @GetMapping("/brands")
    ResponseEntity<List<BrandSelectResponse>> getBrands() {
        return ResponseEntity.ok(adminQueryService.getBrands());
    }

    @PostMapping("/functionalities")
    public ResponseEntity<Void> createFunctionality(
            @RequestBody FunctionalityCreateRequest functionalityCreateRequest
    ) {
        Long functionalityId = adminService.createFunctionality(functionalityCreateRequest);
        return ResponseEntity.created(URI.create("/functionalities/" + functionalityId)).build();
    }

    @ResponseBody
    @GetMapping("/functionalities")
    ResponseEntity<List<FunctionalitySelectResponse>> getFunctionalities() {
        return ResponseEntity.ok(adminQueryService.getFunctionalities());
    }

    @PostMapping("/primary-ingredients")
    public ResponseEntity<Void> createPrimaryIngredient(
            @RequestBody PrimaryIngredientCreateRequest primaryIngredientCreateRequest) {
        Long primaryIngredientId = adminService.createPrimaryIngredient(primaryIngredientCreateRequest);
        return ResponseEntity.created(URI.create("/primary-ingredients/" + primaryIngredientId)).build();
    }

    @ResponseBody
    @GetMapping("/primary-ingredients")
    ResponseEntity<List<PrimaryIngredientSelectResponse>> getPrimaryIngredients() {
        return ResponseEntity.ok(adminQueryService.getPrimaryIngredients());
    }

    @PostMapping("/pet-foods")
    public ResponseEntity<Void> createPetFood(
            @RequestPart PetFoodCreateRequest petFoodCreateRequest,
            @RequestPart MultipartFile image
    ) {
        String imageUrl = imageService.save(image, ImageDirectoryUrl.PET_FOOD_DIRECTORY);
        Long petFoodId = adminService.createPetFood(petFoodCreateRequest, imageUrl);
        return ResponseEntity.created(URI.create("/pet-foods/" + petFoodId)).build();
    }

}
