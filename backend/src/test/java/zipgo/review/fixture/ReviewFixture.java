package zipgo.review.fixture;

import java.util.List;
import zipgo.member.domain.Member;
import zipgo.petfood.domain.PetFood;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.StoolCondition;
import zipgo.review.domain.TastePreference;

public class ReviewFixture {

    public static Review 극찬_리뷰_생성(Member 멤버, PetFood 식품) {
        return Review.builder()
                .member(멤버)
                .petFood(식품)
                .rating(5)
                .comment("우리 아이랑 너무 잘 맞아요!")
                .tastePreference(TastePreference.EATS_VERY_WELL)
                .stoolCondition(StoolCondition.SOFT_MOIST)
                .build();
    }

    public static Review 혹평_리뷰_생성(Member 멤버, PetFood 식품, List<AdverseReaction> 이상반응들) {
        Review 리뷰 = Review.builder()
                .member(멤버)
                .petFood(식품)
                .rating(1)
                .comment("우리 아이가 한 입 먹고 더 안 먹어요 ㅡ.ㅡ 책임지세요.")
                .tastePreference(TastePreference.NOT_AT_ALL)
                .stoolCondition(StoolCondition.DIARRHEA)
                .build();
        리뷰.addAdverseReactions(이상반응들);
        return 리뷰;
    }

}
