package zipgo.review.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zipgo.review.domain.Review;

import static zipgo.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findAllByPetFoodId(Long petFoodId, int size, Long cursor) {
        return queryFactory.selectFrom(review)
                .where(equalsPetFoodId(petFoodId), afterThan(cursor))
                .orderBy(review.id.desc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression equalsPetFoodId(Long petFoodId) {
        if (petFoodId == null) {
            return null;
        }
        return review.petFood.id.eq(petFoodId);
    }

    private BooleanExpression afterThan(Long lastReviewId) {
        if (lastReviewId == null) {
            return null;
        }
        return review.id.lt(lastReviewId);
    }

}
