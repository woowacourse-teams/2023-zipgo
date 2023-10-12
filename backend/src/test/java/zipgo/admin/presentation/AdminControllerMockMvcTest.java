package zipgo.admin.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import zipgo.common.acceptance.MockMvcTest;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.admin.dto.PetFoodCreateRequest;
import zipgo.petfood.domain.fixture.PetFoodFixture;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zipgo.brand.domain.fixture.BrandFixture.무민_브랜드_생성_요청;

class AdminControllerMockMvcTest extends MockMvcTest {

    @Test
    void 브랜드를_생성하면_201이_반환된다() throws Exception {
        // given
        var 사진_파일 = new MockMultipartFile("image", "사진.png", "image/png", "사진".getBytes());
        BrandCreateRequest brandCreateRequest = 무민_브랜드_생성_요청();
        String requestToJson = objectMapper.writeValueAsString(brandCreateRequest);
        MockMultipartFile 브랜드_생성_요청 = new MockMultipartFile("brandCreateRequest",
                "brandCreateRequest",
                "application/json",
                requestToJson.getBytes(StandardCharsets.UTF_8));

        when(imageService.save(사진_파일, "directory"))
                .thenReturn("사진_주소");

        // when
        var 요청 = mockMvc.perform(multipart("/admin/brands")
                        .file(사진_파일)
                        .file(브랜드_생성_요청)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(성공_API_문서_생성());

        // then
        요청.andExpect(status().isCreated());
    }

    private RestDocumentationResultHandler 성공_API_문서_생성() {
        var 문서_정보 = resourceDetails().summary("브랜드 생성").description("브랜드를 생성합니다.");
        return MockMvcRestDocumentationWrapper.document("브랜드 생성 - 성공",
                문서_정보,
                requestParts(
                        partWithName("image").description("브랜드 사진"),
                        partWithName("brandCreateRequest").description("브랜드 생성 요청").attributes(
                                key("contentType").value("application/json")
                        )));
    }

    @Nested
    class 식품_생성 {

        @Test
        void 식품을_생성하면_201이_반환된다() throws Exception {
            // given
            var 사진_파일 = new MockMultipartFile("image", "사진.png", "image/png", "사진".getBytes());
            PetFoodCreateRequest petFoodCreateRequest = PetFoodFixture.식품_생성_요청(100L, List.of(1L, 2L), List.of(1L, 2L));
            String requestToJson = objectMapper.writeValueAsString(petFoodCreateRequest);
            MockMultipartFile 식품_생성_요청 = new MockMultipartFile("petFoodCreateRequest",
                    "petFoodCreateRequest",
                    "application/json",
                    requestToJson.getBytes(StandardCharsets.UTF_8));

            when(imageService.save(사진_파일, "directory"))
                    .thenReturn("사진_주소");

            // when
            var 요청 = mockMvc.perform(multipart("/admin/pet-foods")
                            .file(사진_파일)
                            .file(식품_생성_요청)
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andDo(성공_API_문서_생성());

            // then
            요청.andExpect(status().isCreated());
        }

        private RestDocumentationResultHandler 성공_API_문서_생성() {
            var 문서_정보 = resourceDetails().summary("식품 생성").description("식품을 생성합니다.");
            return MockMvcRestDocumentationWrapper.document("식품 생성 - 성공",
                    문서_정보,
                    requestParts(
                            partWithName("image").description("식품 사진"),
                            partWithName("petFoodCreateRequest").description("식품 생성 요청").attributes(
                                    key("contentType").value("application/json")
                            )));
        }

    }

}
