package zipgo.review.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.review.domain.HelpfulReaction;

public interface HelpfulReactionRepository extends JpaRepository<HelpfulReaction, Long> {

    public void deleteByReviewIdAndMadeById(Long reviewId, Long madeById);

}
