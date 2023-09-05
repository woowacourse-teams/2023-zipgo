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
import zipgo.petfood.application.PrimaryIngredientService;
import zipgo.petfood.presentation.dto.PrimaryIngredientCreateRequest;
import zipgo.petfood.presentation.dto.PrimaryIngredientSelectResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/primary-ingredients")
public class PrimaryIngredientController {
        private final PrimaryIngredientService primaryIngredientService;

        @PostMapping
        public ResponseEntity<Void> createPrimaryIngredient(
                @RequestBody PrimaryIngredientCreateRequest primaryIngredientCreateRequest) {
                Long primaryIngredientId = primaryIngredientService.createPrimaryIngredient(primaryIngredientCreateRequest);
                return ResponseEntity.created(URI.create("/primary-ingredients/" + primaryIngredientId)).build();
        }

        @GetMapping
        @ResponseBody
        ResponseEntity<List<PrimaryIngredientSelectResponse>> getFunctionalities() {
                return ResponseEntity.ok(primaryIngredientService.getPrimaryIngredients());
        }

}
