package zipgo.auth.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import zipgo.auth.dto.TokenDto;
import zipgo.auth.exception.OAuthTokenNotBringException;
import zipgo.common.acceptance.MockMvcTest;
import zipgo.pet.domain.fixture.PetFixture;

import java.util.List;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;

class AuthControllerMockTest extends MockMvcTest {

    private static final Schema 응답_형식 = Schema.schema("TokenResponse");
    private static final ResourceSnippetDetails 문서_정보 = resourceDetails().summary("로그인")
            .description("로그인 합니다.")
            .responseSchema(응답_형식);

    @Test
    void 로그인_성공() throws Exception {
        // given
        var 토큰 = TokenDto.of("accessTokenValue", "refreshTokenValue");
        when(authServiceFacade.login("인가_코드", "리다이렉트 유알아이"))
                .thenReturn(토큰);
        var 리프레시_토큰_쿠키 = ResponseCookie.from("refreshToken", 토큰.refreshToken()).build();
        when(refreshTokenCookieProvider.createCookie(토큰.refreshToken()))
                .thenReturn(리프레시_토큰_쿠키);
        when(jwtProvider.getPayload(토큰.accessToken()))
                .thenReturn("1");
        when(memberQueryService.findById(1L))
                .thenReturn(식별자_있는_멤버());
        when(petQueryService.readMemberPets(1L))
                .thenReturn(List.of(PetFixture.반려동물()));

        // when
        var 요청 = mockMvc.perform(post("/auth/login")
                        .param("code", "인가_코드")
                        .param("redirect-uri", "리다이렉트 유알아이"))
                .andDo(로그인_성공_문서_생성());

        // then
        요청.andExpect(status().isOk());
    }

    @Test
    void 로그인_성공_후_사용자의_반려동물이_없다면_pets는_빈_배열이다() throws Exception {
        // given
        var 토큰 = TokenDto.of("accessTokenValue", "refreshTokenValue");
        when(authServiceFacade.login("인가_코드", "리다이렉트 유알아이"))
                .thenReturn(토큰);
        var 리프레시_토큰_쿠키 = ResponseCookie.from("refreshToken", 토큰.refreshToken()).build();
        when(refreshTokenCookieProvider.createCookie(토큰.refreshToken()))
                .thenReturn(리프레시_토큰_쿠키);
        when(jwtProvider.getPayload(토큰.accessToken()))
                .thenReturn("1");
        when(memberQueryService.findById(1L))
                .thenReturn(식별자_있는_멤버());
        when(petQueryService.readMemberPets(1L))
                .thenReturn(EMPTY_LIST);

        // when
        var 요청 = mockMvc.perform(post("/auth/login")
                        .param("code", "인가_코드")
                        .param("redirect-uri", "리다이렉트 유알아이"))
                .andDo(로그인_성공_반려동물_정보_없음_문서_생성());

        // then
        요청.andExpect(status().isOk());
    }

    @Test
    void 자원_서버의_토큰을_가져오는데_실패하면_예외가_발생한다() throws Exception {
        // given
        when(authServiceFacade.login("인가_코드", "리다이렉트 유알아이"))
                .thenThrow(new OAuthTokenNotBringException());

        // when
        var 요청 = mockMvc.perform(post("/auth/login")
                .param("code", "인가_코드")
                .param("redirect-uri", "리다이렉트 유알아이"));

        // then
        요청.andExpect(status().isBadGateway());
    }

    private RestDocumentationResultHandler 로그인_성공_문서_생성() {
        return MockMvcRestDocumentationWrapper.document("로그인 성공 - 반려동물 기등록",
                문서_정보,
                queryParameters(
                        parameterWithName("code").optional().description("로그인 API")
                ),
                responseFields(
                        fieldWithPath("accessToken").description("accessToken").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.id").description("사용자 식별자").type(JsonFieldType.NUMBER),
                        fieldWithPath("authResponse.name").description("사용자 이름").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.email").description("사용자 이메일").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.profileImageUrl").description("사용자 프로필 사진").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.hasPet").description("반려동물 등록 여부").type(JsonFieldType.BOOLEAN),
                        fieldWithPath("authResponse.pets[].id").description("반려동물 식별자"),
                        fieldWithPath("authResponse.pets[].name").description("반려동물 이름").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].age").description("반려동물 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("authResponse.pets[].breedId").description("반려동물 견종 식별자"),
                        fieldWithPath("authResponse.pets[].breed").description("반려동물 견종").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].ageGroupId").description("반려동물 나이그룹 식별자").type(JsonFieldType.NUMBER),
                        fieldWithPath("authResponse.pets[].ageGroup").description("반려동물 나이그룹").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].petSize").description("반려동물 크기").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].gender").description("반려동물 성별").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].weight").description("반려동물 몸무게").type(JsonFieldType.NUMBER),
                        fieldWithPath("authResponse.pets[].imageUrl").description("반려동물 사진 주소").type(JsonFieldType.STRING)
                ));
    }

    private RestDocumentationResultHandler 로그인_성공_반려동물_정보_없음_문서_생성() {
        return MockMvcRestDocumentationWrapper.document("로그인 성공 - 반려동물 미등록",
                문서_정보,
                queryParameters(
                        parameterWithName("code").optional().description("로그인 API")
                ),
                responseFields(
                        fieldWithPath("accessToken").description("accessToken").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.id").description("사용자 식별자").type(JsonFieldType.NUMBER),
                        fieldWithPath("authResponse.name").description("사용자 이름").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.email").description("사용자 이메일").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.profileImageUrl").description("사용자 프로필 사진").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.hasPet").description("반려동물 등록 여부").type(JsonFieldType.BOOLEAN),
                        fieldWithPath("authResponse.pets").description("반려동물 프로필").type(JsonFieldType.ARRAY)
                ));
    }

}
