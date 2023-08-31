package zipgo.admin.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zipgo.admin.dto.BrandSelectResponse;
import zipgo.brand.application.BrandQueryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final BrandQueryService brandQueryService;

    @GetMapping
    String home() {
        return "admin/home";
    }

    @ResponseBody
    @GetMapping("/brands")
    ResponseEntity<List<BrandSelectResponse>> getBrands() {
        return ResponseEntity.ok(brandQueryService.getBrands());
    }

}
