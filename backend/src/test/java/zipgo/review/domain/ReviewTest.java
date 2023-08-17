package zipgo.review.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import zipgo.auth.exception.AuthException;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Pet;
import zipgo.petfood.domain.PetFood;
import zipgo.review.fixture.ReviewFixture;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ReviewTest {

    @ParameterizedTest
    @ValueSource(longs = {2L, 200L, 30L, 19L})
    void 작성자가_아닐경우_예외가_발생한다(Long 다른_회원의_아이디) {
        //given
        Member 작성자 = Member.builder().id(1L).build();
        Review 리뷰 = 리뷰_작성(작성자);

        //expect
        assertThatThrownBy(() -> 리뷰.validateOwner(다른_회원의_아이디))
                .isInstanceOf(AuthException.Forbidden.class);
    }

    private Review 리뷰_작성(Member 작성자) {
        PetFood 식품 = PetFood.builder().build();
        Pet 반려동물 = Pet.builder().owner(작성자).build();
        Review 리뷰 = ReviewFixture.극찬_리뷰_생성(반려동물, 식품, emptyList());
        return 리뷰;
    }

    @Nested
    class 도움이돼요 {

        @Test
        void 다른_회원_리뷰에_도움이돼요를_추가한다() {
            //given
            Member 작성자 = Member.builder().id(1L).build();
            Review 리뷰 = 리뷰_작성(작성자);
            Member 회원 = Member.builder()
                    .id(2L)
                    .build();

            //when
            리뷰.reactedBy(회원);

            //then
            assertThat(리뷰.getHelpfulReactions())
                    .extracting(HelpfulReaction::getMadeBy)
                    .filteredOn(회원::equals)
                    .hasSize(1);
        }

        @Test
        void 내가_쓴_리뷰에_도움이돼요를_추가하면_예외가_발생한다() {
            //given
            Member 나 = Member.builder().id(1L).build();
            Review 리뷰 = 리뷰_작성(나);

            //when
            //then
            assertThatThrownBy(() -> 리뷰.reactedBy(나))
                    .isInstanceOf(ReviewSelfReactedException.class);
        }

        @Test
        void 이미_도움이_돼요를_누른_경우_아무_일도_일어나지_않는다() {
            Member 작성자 = Member.builder().id(1L).build();
            Review 리뷰 = 리뷰_작성(작성자);
            Member 회원 = Member.builder()
                    .id(2L)
                    .build();
            리뷰.reactedBy(회원);

            //when
            리뷰.reactedBy(회원);

            //then
            assertThat(리뷰.getHelpfulReactions())
                    .extracting(HelpfulReaction::getMadeBy)
                    .filteredOn(회원::equals)
                    .hasSize(1);
        }

    }

    @Nested
    class 도움이돼요_취소 {

        @Test
        void 도움이_돼요를_취소한다() {
            //given
            Member 작성자 = Member.builder().id(1L).build();
            Review 리뷰 = 리뷰_작성(작성자);
            Member 회원 = Member.builder()
                    .id(2L)
                    .build();
            리뷰.reactedBy(회원);

            //when
            리뷰.removeReactionBy(회원);

            //then
            assertThat(리뷰.getHelpfulReactions())
                    .extracting(HelpfulReaction::getMadeBy)
                    .noneMatch(회원::equals);
        }

        @Test
        void 누른적없는데_취소한다() {
            //given
            Member 작성자 = Member.builder().id(1L).build();
            Review 리뷰 = 리뷰_작성(작성자);
            Member 회원 = Member.builder()
                    .id(2L)
                    .build();

            //when
            리뷰.removeReactionBy(회원);

            //then
            assertThat(리뷰.getHelpfulReactions())
                    .extracting(HelpfulReaction::getMadeBy)
                    .noneMatch(회원::equals);
        }

    }

    @Test
    void 회원_아이디가_null일경우_false_반환() {
        //given
        Member 작성자 = Member.builder().id(1L).build();
        PetFood 식품 = PetFood.builder().build();
        Pet 반려동물 = Pet.builder().owner(작성자).build();
        Review 리뷰 = ReviewFixture.극찬_리뷰_생성(반려동물, 식품, emptyList());

        //when
        boolean reactedBy = 리뷰.isReactedBy(null);

        //then
        assertThat(reactedBy).isFalse();
    }

}
