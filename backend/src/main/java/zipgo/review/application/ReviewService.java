package zipgo.review.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.dto.request.CreateReviewRequest;
import zipgo.review.dto.request.UpdateReviewRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PetFoodRepository petFoodRepository;
    private final PetRepository petRepository;

    public Long createReview(Long memberId, CreateReviewRequest request) {
        Member member = memberRepository.getById(memberId);
        Pet pet = petRepository.getById(request.petId());
        if (!member.isOwnerOf(pet)) {
            throw new IllegalArgumentException();
        }

        PetFood petFood = petFoodRepository.getById(request.petFoodId());
        Review review = request.toEntity(pet, petFood);
        reviewRepository.save(review);
        return review.getId();
    }

    public void updateReview(Long memberId, Long reviewId, UpdateReviewRequest request) {
        Review review = reviewRepository.getById(reviewId);

        review.validateOwner(memberId);
        update(request, review);
    }

    private void update(UpdateReviewRequest request, Review review) {
        review.updateRating(request.rating());
        review.updateComment(request.comment());
        review.updateTastePreference(request.tastePreference());
        review.updateStoolCondition(request.stoolCondition());

        review.removeAdverseReactions();
        review.addAdverseReactions(request.adverseReactions());
    }

    public void deleteReview(Long memberId, Long reviewId) {
        Review review = reviewRepository.getById(reviewId);

        review.validateOwner(memberId);
        reviewRepository.delete(review);
    }

    public void addHelpfulReaction(Long memberId, Long reviewId) {
        Review review = reviewRepository.getById(reviewId);
        Member member = memberRepository.getById(memberId);

        review.reactedBy(member);
    }

    public void removeHelpfulReaction(Long memberId, Long reviewId) {
        Review review = reviewRepository.getById(reviewId);
        Member member = memberRepository.getById(memberId);
        review.removeReactionBy(member);
    }

}
