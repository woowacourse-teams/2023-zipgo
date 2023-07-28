package zipgo.review.fixture;

import static zipgo.review.domain.StoolCondition.DIARRHEA;
import static zipgo.review.domain.StoolCondition.SOFT_MOIST;
import static zipgo.review.domain.TastePreference.EATS_VERY_WELL;
import static zipgo.review.domain.TastePreference.NOT_AT_ALL;

import java.util.List;
import zipgo.member.domain.Member;
import zipgo.petfood.domain.PetFood;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;

public class ReviewFixture {

    public static Review 극찬_리뷰_생성(Member 멤버, PetFood 식품) {
        return Review.builder()
                .member(멤버)
                .petFood(식품)
                .rating(5)
                .comment("우리 아이랑 너무 잘 맞아요!")
                .tastePreference(EATS_VERY_WELL)
                .stoolCondition(SOFT_MOIST)
                .build();
    }

    public static Review 혹평_리뷰_생성(Member 멤버, PetFood 식품, List<AdverseReaction> 이상반응들) {
        Review 리뷰 = Review.builder()
                .member(멤버)
                .petFood(식품)
                .rating(1)
                .comment("우리 아이가 한 입 먹고 더 안 먹어요 ㅡ.ㅡ 책임지세요.")
                .tastePreference(NOT_AT_ALL)
                .stoolCondition(DIARRHEA)
                .build();
        리뷰.addAdverseReactions(이상반응들);
        return 리뷰;
    }

}
