package zipgo.petfood.presentation;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import zipgo.admin.dto.BrandSelectResponse;
import zipgo.petfood.application.FunctionalityService;
import zipgo.petfood.presentation.dto.FunctionalityCreateRequest;
import zipgo.petfood.presentation.dto.FunctionalitySelectResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/functionalities")
public class FunctionalityController {

    private final FunctionalityService functionalityService;

    @PostMapping
    public ResponseEntity<Void> createFunctionality(
            @RequestBody FunctionalityCreateRequest functionalityCreateRequest
    ) {
        Long functionalityId = functionalityService.createFunctionality(functionalityCreateRequest);
        return ResponseEntity.created(URI.create("/functionalities/" + functionalityId)).build();
    }

    @GetMapping
    @ResponseBody
    ResponseEntity<List<FunctionalitySelectResponse>> getFunctionalities() {
        return ResponseEntity.ok(functionalityService.getFunctionalities());
    }

}
