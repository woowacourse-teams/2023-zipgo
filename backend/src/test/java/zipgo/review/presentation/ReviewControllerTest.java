package zipgo.review.presentation;

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.acceptance.AcceptanceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.fixture.MemberFixture;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.fixture.ReviewFixture;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetFixture.반려동물;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.review.fixture.MemberFixture.무민;
import static zipgo.review.fixture.ReviewFixture.극찬_리뷰_생성;
import static zipgo.review.fixture.ReviewFixture.리뷰_생성_요청;
import static zipgo.review.fixture.ReviewFixture.리뷰_수정_요청;


public class ReviewControllerTest extends AcceptanceTest {

    private PetFood 식품;

    private Review 리뷰;

    private Long 잘못된_id = 123456789L;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        Brand 브랜드 = brandRepository.save(BrandFixture.오리젠_식품_브랜드_생성());
        식품 = 모든_영양기준_만족_식품(브랜드);
        petFoodRepository.save(식품);

        Member 멤버 = memberRepository.save(무민());
        PetSize 사이즈 = petSizeRepository.save(소형견());
        Breeds 종류 = breedsRepository.save(견종(사이즈));
        Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
        리뷰 = reviewRepository.save(ReviewFixture.극찬_리뷰_생성(반려동물, 식품, List.of("없어요")));
    }

    @Nested
    @DisplayName("리뷰 전체 목록 조회 API")
    class GetReviews {

        private Schema 성공_응답_형식 = schema("GetReviewsResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("리뷰 전체 목록 조회하기")
                .description("해당 반려동물 식품에 대한 모든 리뷰를 조회합니다.");

        @Test
        void 사이즈_없이_리뷰를_조회하면_10개_이하가_온다() {
            // given
            리뷰_여러개_생성();
            var 요청_준비 = given(spec).contentType(JSON).filter(리뷰_전체_목록_조회_API_문서_생성("리뷰 전체 조회 - 성공"));

            // when
            var 응답 = 요청_준비.when()
                    .queryParam("petFoodId", 식품.getId())
                    .get("/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("reviews.size()", is(10));
        }

        @Test
        void 리뷰를_원하는_사이즈만큼_반환() {
            //given
            리뷰_여러개_생성();

            var 요청_준비 = given(spec).contentType(JSON);

            // when
            var 응답 = 요청_준비.when()
                    .queryParam("petFoodId", 식품.getId())
                    .queryParam("size", 10)
                    .get("/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("reviews.size()", is(10));
        }

        @Test
        void 마지막일_경우() {
            //given
            리뷰_하나빼고_삭제();
            var 요청_준비 = given(spec).contentType(JSON);

            // when
            var 응답 = 요청_준비.when()
                    .queryParam("petFoodId", 식품.getId())
                    .queryParam("size", 10)
                    .get("/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("reviews.size()", lessThan(10));
        }

        private RestDocumentationFilter 리뷰_전체_목록_조회_API_문서_생성(String uniqueName) {
            return document(uniqueName, 문서_정보.responseSchema(성공_응답_형식),
                    queryParameters(
                            parameterWithName("petFoodId").description("식품 id"),
                            parameterWithName("size").description("조회하고자 하는 리뷰의 개수").optional(),
                            parameterWithName("lastReviewId").description("이전 페이지의 마지막 리뷰 id").optional(),
                            parameterWithName("petSizeId").description("반려동물의 견종 크기 id").optional(),
                            parameterWithName("ageGroupId").description("반려동물의 나이 그룹 id").optional(),
                            parameterWithName("breedId").description("반려동물의 견종 id").optional(),
                            parameterWithName("sortBy").description("정렬 기준").optional()),
                    responseFields(fieldWithPath("reviews[].id").description("리뷰 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].rating").description("리뷰 별점").type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].date").description("리뷰 생성일").type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].comment").description("리뷰 코멘트").type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].tastePreference").description("기호성").type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].stoolCondition").description("대변 상태").type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].adverseReactions").description("이상 반응들")
                                    .type(JsonFieldType.ARRAY),
                            fieldWithPath("reviews[].petProfile.id").description("반려동물 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].petProfile.name").description("반려동물 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].petProfile.profileUrl").description("반려동물 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].petProfile.writtenAge").description("반려동물 나이")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].petProfile.writtenWeight").description("반려동물 몸무게")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].petProfile.breed.id").description("반려동물 견종 id")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].petProfile.breed.name").description("반려동물 견종 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].petProfile.breed.size.id").description("반려동물 견종 크기 id")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].petProfile.breed.size.name").description("반려동물 견종 크기 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("reviews[].helpfulReaction.count").description("도움이 되었어요 수")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("reviews[].helpfulReaction.reacted").description("도움이 되었어요를 눌렀는지")
                                    .type(JsonFieldType.BOOLEAN)));

        }

        private void 리뷰_여러개_생성() {
            List<Review> 리뷰들 = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Member 멤버 = memberRepository.save(MemberFixture.식별자_없는_멤버("email" + i));
                PetSize 사이즈 = petSizeRepository.save(소형견());
                Breeds 종류 = breedsRepository.save(견종(사이즈));
                Pet 반려동물 = petRepository.save(반려동물(멤버, 종류));
                Review 리뷰 = 극찬_리뷰_생성(반려동물, 식품,
                        List.of("없어요"));
                리뷰들.add(리뷰);
            }
            reviewRepository.saveAll(리뷰들);
        }

        private void 리뷰_하나빼고_삭제() {
            List<Review> 리뷰들 = reviewRepository.findAll().stream().skip(1).collect(Collectors.toList());
            reviewRepository.deleteAllById(리뷰들.stream().map(Review::getId).toList());
        }

        @Test
        void 사이즈를_0으로_했을_때() {
            //given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(API_예외응답_문서_생성("실패 - size가 0일 때"));

            // when
            var 응답 = 요청_준비.when()
                    .queryParam("petFoodId", 식품.getId())
                    .queryParam("size", 0)
                    .get("/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(BAD_REQUEST.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성(String uniqueName) {
            return document(uniqueName, 문서_정보.responseSchema(에러_응답_형식));
        }

    }

    @Nested
    @DisplayName("리뷰 개별 조회 API")
    class GetReview {

        private Schema 성공_응답_형식 = schema("GetReviewResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("리뷰 개별 조회하기")
                .description("해당 반려동물 식품에 대한 개별 리뷰를 조회합니다.");

        @Test
        void 리뷰를_조회하면_201_반환() {
            // given
            var 요청_준비 = given(spec).contentType(JSON).filter(리뷰_개별_API_문서_생성());

            // when
            var 응답 = 요청_준비.when().pathParam("reviewId", 리뷰.getId()).get("/reviews/{reviewId}");

            // then
            응답.then().assertThat().statusCode(OK.value());
        }

        private RestDocumentationFilter 리뷰_개별_API_문서_생성() {
            return document("리뷰 개별 조회 - 성공", 문서_정보.responseSchema(성공_응답_형식),
                    pathParameters(parameterWithName("reviewId").description("리뷰 id")),
                    responseFields(fieldWithPath("id").description("리뷰 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("rating").description("리뷰 별점").type(JsonFieldType.NUMBER),
                            fieldWithPath("date").description("리뷰 생성일").type(JsonFieldType.STRING),
                            fieldWithPath("comment").description("리뷰 코멘트").type(JsonFieldType.STRING),
                            fieldWithPath("tastePreference").description("기호성").type(JsonFieldType.STRING),
                            fieldWithPath("stoolCondition").description("대변 상태").type(JsonFieldType.STRING),
                            fieldWithPath("adverseReactions").description("이상 반응들").type(JsonFieldType.ARRAY),
                            fieldWithPath("petProfile.id").description("반려동물 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("petProfile.name").description("반려동물 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("petProfile.profileUrl").description("반려동물 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("petProfile.writtenAge").description("반려동물 나이")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("petProfile.writtenWeight").description("반려동물 몸무게")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("petProfile.breed.id").description("반려동물 견종 id")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("petProfile.breed.name").description("반려동물 견종 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("petProfile.breed.size.id").description("반려동물 견종 크기 id")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("petProfile.breed.size.name").description("반려동물 견종 크기 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("helpfulReaction.count").description("도움이 되었어요 수")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("helpfulReaction.reacted").description("도움이 되었어요를 눌렀는지")
                                    .type(JsonFieldType.BOOLEAN)));
        }

    }

    @Nested
    @DisplayName("리뷰 생성 API")
    class CreateReviews {

        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("리뷰 생성하기")
                .description("해당 반려동물 식품에 대한 리뷰를 생성합니다.");
        private Schema 요청_형식 = schema("CreateReviewRequest");
        private Schema 성공_응답_형식 = schema("CreateReviewResponse");

        @Test
        void 리뷰를_성공적으로_생성하면_201_반환() {
            // given
            var token = jwtProvider.create("1");
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token)
                    .body(리뷰_생성_요청(식품.getId()))
                    .contentType(JSON).filter(리뷰_생성_API_문서_생성());

            // when
            var 응답 = 요청_준비.when().post("/reviews");

            // then
            응답.then().assertThat().statusCode(CREATED.value());
        }

        private RestDocumentationFilter 리뷰_생성_API_문서_생성() {
            return document("리뷰 생성 - 성공", 문서_정보.requestSchema(요청_형식).responseSchema(성공_응답_형식),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                    requestFields(fieldWithPath("petFoodId").description("식품 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("rating").description("리뷰 별점").type(JsonFieldType.NUMBER),
                            fieldWithPath("comment").description("리뷰 코멘트").optional().type(JsonFieldType.STRING),
                            fieldWithPath("tastePreference").description("기호성").type(JsonFieldType.STRING),
                            fieldWithPath("stoolCondition").description("대변 상태").type(JsonFieldType.STRING),
                            fieldWithPath("adverseReactions").description("이상 반응들").type(JsonFieldType.ARRAY)));
        }

        @Test
        void 없는_식품에_대해_리뷰를_생성하면_404_반환() {
            // given
            var token = jwtProvider.create("1");
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token)
                    .body(리뷰_생성_요청(잘못된_id)).contentType(JSON)
                    .filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().post("/reviews");

            // then
            응답.then().assertThat().statusCode(NOT_FOUND.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성() {
            return document("리뷰 생성 - 실패(없는 식품)", 문서_정보.responseSchema(에러_응답_형식),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")));
        }

    }

    @Nested
    @DisplayName("리뷰 수정 API")
    class UpdateReviews {

        private Schema 요청_형식 = schema("UpdateReviewRequest");
        private Schema 성공_응답_형식 = schema("UpdateReviewResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("리뷰 수정하기")
                .description("해당 반려동물 식품에 대한 리뷰를 수정합니다.");

        @Test
        void 리뷰를_성공적으로_수정하면_204_반환() {
            // given
            var token = jwtProvider.create("1");
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token)
                    .body(리뷰_수정_요청()).contentType(JSON)
                    .filter(리뷰_수정_API_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .pathParam("reviewId", 리뷰.getId())
                    .put("/reviews/{reviewId}");

            // then
            응답.then().assertThat().statusCode(NO_CONTENT.value());
        }

        private RestDocumentationFilter 리뷰_수정_API_문서_생성() {
            return document("리뷰 수정 - 성공", 문서_정보.requestSchema(요청_형식).responseSchema(성공_응답_형식),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                    pathParameters(parameterWithName("reviewId").description("리뷰 id")),
                    requestFields(fieldWithPath("rating").description("리뷰 별점").type(JsonFieldType.NUMBER),
                            fieldWithPath("comment").description("리뷰 코멘트").optional().type(JsonFieldType.STRING),
                            fieldWithPath("tastePreference").description("기호성").type(JsonFieldType.STRING),
                            fieldWithPath("stoolCondition").description("대변 상태").type(JsonFieldType.STRING),
                            fieldWithPath("adverseReactions").description("이상 반응들").type(JsonFieldType.ARRAY)));
        }

        @Test
        void 리뷰를_쓴_사람이_아닌_멤버가_리뷰를_수정하면_403_반환() {
            // given
            var notOwnerToken = jwtProvider.create("2");
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + notOwnerToken)
                    .body(리뷰_생성_요청(식품.getId()))
                    .contentType(JSON).filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().pathParam("reviewId", 리뷰.getId()).put("/reviews/{reviewId}");

            // then
            응답.then().assertThat().statusCode(FORBIDDEN.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성() {
            return document("리뷰 수정 - 실패(Forbidden)", 문서_정보.responseSchema(에러_응답_형식),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")));
        }

    }

    @Nested
    @DisplayName("리뷰 삭제 API")
    class DeleteReviews {

        private Schema 성공_응답_형식 = schema("DeleteReviewResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("리뷰 삭제하기")
                .description("해당 반려동물 식품에 대한 리뷰를 삭제합니다.");


        @Test
        void 리뷰를_성공적으로_삭제하면_204_반환() {
            // given
            var token = jwtProvider.create("1");
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token)
                    .body(리뷰_수정_요청()).contentType(JSON)
                    .filter(리뷰_삭제_API_문서_생성());

            // when
            var 응답 = 요청_준비.when().pathParam("reviewId", 리뷰.getId()).delete("/reviews/{reviewId}");

            // then
            응답.then().assertThat().statusCode(NO_CONTENT.value());
        }

        private RestDocumentationFilter 리뷰_삭제_API_문서_생성() {
            return document("리뷰 삭제 - 성공", 문서_정보.responseSchema(성공_응답_형식),
                    pathParameters(parameterWithName("reviewId").description("리뷰 id")),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")));
        }

        @Test
        void 리뷰를_쓴_사람이_아닌_멤버가_리뷰를_삭제하면_403_반환() {
            // given
            var notOwnerToken = jwtProvider.create("2");
            var 요청_준비 = given(spec)
                    .header("Authorization", "Bearer " + notOwnerToken)
                    .contentType(JSON)
                    .filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().pathParam("reviewId", 리뷰.getId()).delete("/reviews/{reviewId}");

            // then
            응답.then().assertThat().statusCode(FORBIDDEN.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성() {
            return document("리뷰 삭제 - 실패(Forbidden)", 문서_정보.responseSchema(에러_응답_형식),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")));
        }

    }

    @Nested
    @DisplayName("리뷰 메타데이터 조회 API")
    class GetMetadata {

        private Schema 성공_응답_형식 = schema("ReviewMetadataResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("리뷰 메타데이터 조회하기")
                .description("리뷰 메타데이터를 조회합니다.");

        @Test
        void 리뷰_메타데이터_조회하기() {
            //given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(리뷰_메타데이터_조회_API_문서_생성());

            //when
            var 응답 = 요청_준비.when()
                    .get("/reviews/metadata");

            //then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        private RestDocumentationFilter 리뷰_메타데이터_조회_API_문서_생성() {
            return document("리뷰 메타데이터 조회 - 성공",
                    문서_정보.responseSchema(성공_응답_형식),
                    responseFields(
                            fieldWithPath("petSizes").description("반려견 크기 메타데이터").type(JsonFieldType.ARRAY),
                            fieldWithPath("petSizes[].id").description("반려견 크기 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("petSizes[].name").description("반려견 크기 이름").type(JsonFieldType.STRING),
                            fieldWithPath("sortBy").description("리뷰 정렬 기준 메타데이터").type(JsonFieldType.ARRAY),
                            fieldWithPath("sortBy[].id").description("리뷰 정렬 기준 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("sortBy[].name").description("리뷰 정렬 기준 이름").type(JsonFieldType.STRING),
                            fieldWithPath("ageGroups").description("연령대 메타데이터").type(JsonFieldType.ARRAY),
                            fieldWithPath("ageGroups[].id").description("연령대 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("ageGroups[].name").description("연령대 이름").type(JsonFieldType.STRING),
                            fieldWithPath("breeds").description("품종 메타데이터").type(JsonFieldType.ARRAY),
                            fieldWithPath("breeds[].id").description("품종 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("breeds[].name").description("품종 이름").type(JsonFieldType.STRING)
                    )
            );
        }

    }

    @Nested
    @DisplayName("리뷰 요약 조회 API")
    class GetReviewsSummary {

        private Schema 성공_응답_형식 = schema("ReviewSummaryResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("리뷰 요약 조회하기")
                .description("리뷰 요약 정보를 조회합니다.");

        @Test
        void 리뷰_요약_조회하기() {
            //given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(리뷰_요약_조회_API_문서_생성());

            //when
            var 응답 = 요청_준비.when()
                    .param("petFoodId", 식품.getId())
                    .get("/reviews/summary");

            //then
            응답.then().log().all()
                    .assertThat().statusCode(OK.value());
        }

        private RestDocumentationFilter 리뷰_요약_조회_API_문서_생성() {
            return document("리뷰 요약 조회 - 성공", 문서_정보.responseSchema(성공_응답_형식),
                    queryParameters(parameterWithName("petFoodId").description("식품 id")),
                    responseFields(
                            fieldWithPath("rating.average").description("리뷰 총 평점").type(JsonFieldType.NUMBER),
                            fieldWithPath("rating.rating[].name").description("rating 이름").type(JsonFieldType.STRING),
                            fieldWithPath("rating.rating[].percentage").description("rating 해당 백분율")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("tastePreference[].name").description("tastePreference 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("tastePreference[].percentage").description("tastePreference 해당 백분율")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("stoolCondition[].name").description("stoolCondition 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("stoolCondition[].percentage").description("stoolCondition 해당 백분율")
                                    .type(JsonFieldType.NUMBER),
                            fieldWithPath("adverseReaction[].name").description("adverseReaction 이름")
                                    .type(JsonFieldType.STRING),
                            fieldWithPath("adverseReaction[].percentage").description("adverseReaction 해당 백분율")
                                    .type(JsonFieldType.NUMBER)
                    ));
        }

    }

    @Nested
    @DisplayName("리뷰 도움이 돼요 추가 API")
    class AddHelpfulReaction {

        private Schema 성공_응답_형식 = schema("PostHelpfulReactionResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("도움이 돼요 추가하기")
                .description("리뷰에 도움이 돼요를 추가합니다.");

        @Test
        void 도움이돼요_추가_성공() {
            //given
            var 다른_회원 = memberRepository.save(Member.builder().email("도움이돼요_추가할_회원").name("회원명").build());
            var 다른_회원의_JWT = jwtProvider.create(다른_회원.getId().toString());

            var 요청_준비 = given().spec(spec)
                    .contentType(JSON)
                    .filter(API_문서("리뷰 도움이 돼요 추가 - 성공"));

            //when
            var 응답 = 요청_준비.when()
                    .header(AUTHORIZATION, "Bearer " + 다른_회원의_JWT)
                    .pathParam("reviewId", 리뷰.getId())
                    .post("/reviews/{reviewId}/helpful-reactions");

            //then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        @Test
        void 작성자가_도움이돼요를_추가하면_예외가_발생() {
            //given
            var 리뷰_작성자_id = 리뷰.getPet().getOwner().getId();
            var 리뷰_작성자_JWT = jwtProvider.create(리뷰_작성자_id.toString());

            //when
            var 요청_준비 = given().spec(spec)
                    .contentType(JSON)
                    .filter(실패_API_문서("리뷰 도움이 돼요 추가 - 실패 (작성자 본인시도)"));

            //when
            var 응답 = 요청_준비.when()
                    .header(AUTHORIZATION, "Bearer " + 리뷰_작성자_JWT)
                    .pathParam("reviewId", 리뷰.getId())
                    .post("/reviews/{reviewId}/helpful-reactions");

            //then
            응답.then()
                    .log().all()
                    .assertThat()
                    .statusCode(BAD_REQUEST.value());
        }

        @Test
        void 이미_눌렀던_리뷰일_경우() {
            //given
            var 다른_회원 = memberRepository.save(Member.builder().email("도움이돼요_추가할_회원").name("회원명").build());
            var 다른_회원의_JWT = jwtProvider.create(다른_회원.getId().toString());

            given().spec(spec).contentType(JSON)
                    .pathParam("reviewId", 리뷰.getId())
                    .post("/reviews/{reviewId}/helpful-reactions");

            var 요청_준비 = given().spec(spec)
                    .contentType(JSON)
                    .filter(실패_API_문서("리뷰 도움이 돼요 추가 - 성공 (이미 누른 리뷰)"));

            //when
            var 응답 = 요청_준비.when()
                    .header(AUTHORIZATION, "Bearer " + 다른_회원의_JWT)
                    .pathParam("reviewId", 리뷰.getId())
                    .post("/reviews/{reviewId}/helpful-reactions");

            //then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        private RestDocumentationFilter API_문서(String name) {
            return document(name, 문서_정보.responseSchema(성공_응답_형식),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                    pathParameters(parameterWithName("reviewId").description("리뷰 id")));
        }

        private RestDocumentationFilter 실패_API_문서(String name) {
            return document(name, 문서_정보.responseSchema(에러_응답_형식));
        }

    }

    @Nested
    @DisplayName("리뷰 도움이 돼요 취소 API")
    class DeleteHelpfulReaction {

        private Schema 성공_응답_형식 = schema("PostHelpfulReactionResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("도움이 돼요 취소하기")
                .description("눌렀던 도움이 돼요를 취소합니다.");

        @Test
        void 도움이돼요_취소_성공() {
            //given
            var 다른_회원 = memberRepository.save(Member.builder().email("도움이돼요_추가할_회원").name("회원명").build());
            var 다른_회원의_JWT = jwtProvider.create(다른_회원.getId().toString());

            given().spec(spec).contentType(JSON)
                    .pathParam("reviewId", 리뷰.getId())
                    .post("/reviews/{reviewId}/helpful-reactions");

            var 요청_준비 = given().spec(spec)
                    .contentType(JSON)
                    .filter(API_문서("리뷰 도움이 돼요 취소 - 성공"));

            //when
            var 응답 = 요청_준비.when()
                    .header(AUTHORIZATION, "Bearer " + 다른_회원의_JWT)
                    .pathParam("reviewId", 리뷰.getId())
                    .delete("/reviews/{reviewId}/helpful-reactions");

            //then
            응답.then()
                    .assertThat().statusCode(NO_CONTENT.value());
        }

        @Test
        void 누르지_않은_리뷰에서_취소() {
            //given
            var 다른_회원 = memberRepository.save(Member.builder().email("도움이돼요_추가할_회원").name("회원명").build());
            var 다른_회원의_JWT = jwtProvider.create(다른_회원.getId().toString());

            var 요청_준비 = given().spec(spec)
                    .contentType(JSON)
                    .filter(실패_API_문서("리뷰 도움이 돼요 추가 - 성공 (이미 누른 리뷰)"));

            //when
            var 응답 = 요청_준비.when()
                    .header(AUTHORIZATION, "Bearer " + 다른_회원의_JWT)
                    .pathParam("reviewId", 리뷰.getId())
                    .delete("/reviews/{reviewId}/helpful-reactions");

            //then
            응답.then()
                    .assertThat().statusCode(NO_CONTENT.value());
        }

        private RestDocumentationFilter API_문서(String name) {
            return document(name, 문서_정보.responseSchema(성공_응답_형식),
                    requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                    pathParameters(parameterWithName("reviewId").description("리뷰 id")));
        }

        private RestDocumentationFilter 실패_API_문서(String name) {
            return document(name, 문서_정보.responseSchema(에러_응답_형식));
        }

    }

}
