package zipgo.image.presentaion;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zipgo.auth.presentation.AuthInterceptor;
import zipgo.auth.presentation.JwtArgumentResolver;
import zipgo.auth.support.JwtProvider;
import zipgo.image.application.ImageService;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(controllers = ImageController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private AuthInterceptor authInterceptor;

    @MockBean
    private JwtArgumentResolver argumentResolver;

    @Test
    void 사진_등록_성공하면_201_반환() throws Exception {
        // given
        var 사진_파일 = new MockMultipartFile("image", "사진.png", "image/png", "사진".getBytes());

        when(imageService.save(사진_파일))
                .thenReturn("사진_주소");

        // when
        var 요청 = mockMvc.perform(multipart("/images")
                        .file(사진_파일)
                        .header("Authorization", "Bearer a1a2a3.b1b2b3.c1c2c3")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(성공_API_문서_생성());

        // then
        요청.andExpect(status().isCreated());
    }

    private RestDocumentationResultHandler 성공_API_문서_생성() {
        var 문서_정보 = resourceDetails().summary("사진 등록").description("사진을 등록합니다.");
        return MockMvcRestDocumentationWrapper.document("사진 등록 - 성공",
                문서_정보,
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                requestParts(partWithName("image").description("반려견 사진")),
                responseFields(fieldWithPath("imageUrl").description("사진 링크").type(JsonFieldType.STRING))
        );
    }

}
