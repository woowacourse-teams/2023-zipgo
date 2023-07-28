package zipgo.review.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.dto.request.CreateReviewRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PetFoodRepository petFoodRepository;

    public Long createReview(Long memberId, CreateReviewRequest request) {
        Member member = memberRepository.getById(memberId);
        //TODO PetFood pr 머지되면 getById로 리팩터링 할예정
        PetFood petFood = petFoodRepository.findById(request.petFoodId()).orElseThrow();

        Review review = request.toEntity(member, petFood);
        review.addAdverseReactions(request.adverseReactions());

        reviewRepository.save(review);
        return review.getId();
    }

}
