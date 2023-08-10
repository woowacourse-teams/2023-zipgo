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
    public List<Review> findReviewsBy(Long petFoodId, int size, Long lastReviewId) {
        validate(petFoodId, size);
        return queryFactory.selectFrom(review)
                .where(equalsPetFoodId(petFoodId), afterThan(lastReviewId))
                .orderBy(review.id.desc())
                .limit(size)
                .fetch();
    }

    private void validate(Long petFoodId, int size) {
        if (petFoodId == null) {
            throw new IllegalArgumentException("petFoodId는 null이 될 수 없습니다.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size는 0보다 커야 합니다.");
        }
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
