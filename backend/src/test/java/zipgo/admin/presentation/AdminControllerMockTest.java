package zipgo.admin.presentation;

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
import zipgo.admin.application.AdminQueryService;
import zipgo.admin.application.AdminService;
import zipgo.auth.presentation.AuthInterceptor;
import zipgo.auth.presentation.JwtMandatoryArgumentResolver;
import zipgo.auth.support.JwtProvider;
import zipgo.brand.application.BrandService;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.image.application.ImageService;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zipgo.brand.domain.fixture.BrandFixture.무민_브랜드_생성_요청;

@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(controllers = AdminController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AdminControllerMockTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private AdminService adminService;

    @MockBean
    private AdminQueryService adminQueryService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private AuthInterceptor authInterceptor;

    @MockBean
    private JwtMandatoryArgumentResolver argumentResolver;

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

}