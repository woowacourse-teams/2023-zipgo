package zipgo.review.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.review.domain.AdverseReaction;

public interface AdverseReactionRepository extends JpaRepository<AdverseReaction, Long> {

}
