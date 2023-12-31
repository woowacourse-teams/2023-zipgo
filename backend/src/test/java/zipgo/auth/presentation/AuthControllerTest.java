package zipgo.auth.presentation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.auth.domain.RefreshToken;
import zipgo.auth.domain.repository.RefreshTokenRepository;
import zipgo.auth.support.JwtProvider;
import zipgo.common.acceptance.AcceptanceTest;
import zipgo.common.config.JwtCredentials;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

class AuthControllerTest extends AcceptanceTest {

    private static final String TEST_SECRET_KEY = "this1-is2-zipgo3-test4-secret5-key6";

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Nested
    class 토큰_갱신 {

        @Test
        void 엑세스_토큰을_갱신할_수_있다() {
            // given
            var 리프레시_토큰 = jwtProvider.createRefreshToken();
            refreshTokenRepository.save(new RefreshToken(1L, 리프레시_토큰));

            var 요청_준비 = given(spec)
                    .filter(토큰_갱신_성공_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .header("Refresh", "Zipgo " + 리프레시_토큰)
                    .get("/auth/refresh");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        @Test
        void 실패하면_401_반환() {
            var 토큰_생성기 = 유효기간_만료된_jwtProvider_생성();
            var 유효기간_만료된_리프레시_토큰 = 토큰_생성기.createRefreshToken();
            var 요청_준비 = given(spec)
                    .filter(토큰_갱신_실패_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .header("Refresh", "Zipgo " + 유효기간_만료된_리프레시_토큰)
                    .get("/auth/refresh");

            // then
            응답.then()
                    .assertThat().statusCode(UNAUTHORIZED.value());
        }

        private JwtProvider 유효기간_만료된_jwtProvider_생성() {
            return new JwtProvider(
                    new JwtCredentials(
                            TEST_SECRET_KEY,
                            9999999,
                            -99999999
                    )
            );
        }

    }

    @Nested
    class 로그아웃 {

        @Test
        void 로그아웃_성공() {
            // given
            var 엑세스_토큰 = jwtProvider.createAccessToken(1L);
            var 리프레시_토큰 = jwtProvider.createRefreshToken();
            var 요청_준비 = given(spec)
                    .header("Authorization", "Bearer " + 엑세스_토큰)
                    .header("Refresh", "Zipgo " + 리프레시_토큰)
                    .filter(로그아웃_성공_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .post("/auth/logout");

            // then
            응답.then().statusCode(NO_CONTENT.value());
        }

        @Test
        void 엑세스_토큰이_유효하지_않으면_로그아웃_실패() {
            // given
            var 요청_준비 = given(spec)
                    .header("Authorization", "Bearer " + "잘못된토큰이라네")
                    .header("Refresh", "Zipgo " + "잘못된토큰이라네")
                    .filter(로그아웃_실패_문서_생성());

            // when
            var 응답 = 요청_준비.when().post("/auth/logout");

            // then
            응답.then().statusCode(FORBIDDEN.value());
        }

    }

    private RestDocumentationFilter 토큰_갱신_성공_문서_생성() {
        return document("access token 갱신 성공",
                resourceDetails().summary("토큰 갱신").description("access token을 갱신합니다"),
                responseFields(
                        fieldWithPath("accessToken").description("갱신된 accessToken").type(JsonFieldType.STRING)
                ));
    }

    private RestDocumentationFilter 토큰_갱신_실패_문서_생성() {
        return document("access token 갱신 실패 (유효하지 않은 인증 형식)", resourceDetails()
                .summary("토큰 갱신").responseSchema(에러_응답_형식));
    }

    private RestDocumentationFilter 로그아웃_성공_문서_생성() {
        return document("로그아웃 성공", resourceDetails()
                .summary("로그아웃")
        );
    }

    private RestDocumentationFilter 로그아웃_실패_문서_생성() {
        return document("로그아웃 실패 (유효하지 않은 인증 형식)", resourceDetails().summary("로그아웃").responseSchema(에러_응답_형식));
    }

}
