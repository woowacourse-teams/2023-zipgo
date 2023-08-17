package zipgo.review.domain;

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
        PetFood 식품 = PetFood.builder().build();
        Pet 반려동물 = Pet.builder().owner(작성자).build();
        Review 리뷰 = ReviewFixture.극찬_리뷰_생성(반려동물, 식품, emptyList());

        //expect
        assertThatThrownBy(() -> 리뷰.validateOwner(다른_회원의_아이디))
                .isInstanceOf(AuthException.Forbidden.class);
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
