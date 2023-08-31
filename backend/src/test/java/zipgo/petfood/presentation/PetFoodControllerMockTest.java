package zipgo.petfood.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zipgo.auth.presentation.AuthInterceptor;
import zipgo.auth.presentation.JwtMandatoryArgumentResolver;
import zipgo.auth.support.JwtProvider;
import zipgo.image.application.ImageService;
import zipgo.petfood.application.PetFoodQueryService;
import zipgo.petfood.application.PetFoodService;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.presentation.dto.PetFoodCreateRequest;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(controllers = PetFoodController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PetFoodControllerMockTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private PetFoodQueryService petFoodQueryService;

    @MockBean
    private PetFoodService petFoodService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private AuthInterceptor authInterceptor;

    @MockBean
    private JwtMandatoryArgumentResolver argumentResolver;

    @Test
    void 식품을_생성하면_201이_반환된다() throws Exception {
        // given
        var 사진_파일 = new MockMultipartFile("image", "사진.png", "image/png", "사진".getBytes());
        PetFoodCreateRequest petFoodCreateRequest = PetFoodFixture.식품_생성_요청(100L);
        String requestToJson = objectMapper.writeValueAsString(petFoodCreateRequest);
        MockMultipartFile 식품_생성_요청 = new MockMultipartFile("petFoodCreateRequest",
                "petFoodCreateRequest",
                "application/json",
                requestToJson.getBytes(StandardCharsets.UTF_8));

        when(imageService.save(사진_파일, "directory"))
                .thenReturn("사진_주소");

        // when
        var 요청 = mockMvc.perform(multipart("/pet-foods")
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
