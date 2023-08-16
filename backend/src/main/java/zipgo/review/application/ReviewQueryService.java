package zipgo.review.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.review.application.dto.GetReviewQueryRequest;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;
import zipgo.review.dto.response.GetReviewMetadataResponse;
import zipgo.review.dto.response.GetReviewMetadataResponse.Metadata;
import zipgo.review.dto.response.GetReviewsSummaryResponse;
import zipgo.review.dto.response.GetReviewsResponse;
import zipgo.review.dto.response.RatingSummaryElement;
import zipgo.review.dto.response.SummaryElement;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private static final int PERCENTAGE = 100;

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final BreedsRepository breedsRepository;
    private final PetSizeRepository petSizeRepository;
    private final PetFoodQueryRepository petFoodQueryRepository;

    public GetReviewsResponse getReviews(GetReviewQueryRequest dto) {
        List<Review> reviews = reviewQueryRepository.findReviewsBy(dto.petFoodId(), dto.size(), dto.lastReviewId());
        return GetReviewsResponse.from(reviews);
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.getById(reviewId);
    }

    public GetReviewMetadataResponse getReviewMetadata() {
        List<Metadata> breedSizes = findAllBreedSizes();
        List<Metadata> sorters = findAllSorters();
        List<Metadata> ageGroups = findAllAgeGroups();
        List<Metadata> breeds = findAllBreeds();

        return new GetReviewMetadataResponse(breedSizes, sorters, ageGroups, breeds);
    }

    private List<Metadata> findAllBreeds() {
        return breedsRepository.findAll().stream()
                .map(breed -> new Metadata(breed.getId(), breed.getName()))
                .toList();
    }

    private List<Metadata> findAllAgeGroups() {
        return Arrays.stream(AgeGroup.values())
                .map(ageGroup -> new Metadata(ageGroup.getId(), ageGroup.getName()))
                .toList();
    }

    private List<Metadata> findAllSorters() {
        return Arrays.stream(SortBy.values())
                .map(sortBy -> new Metadata(sortBy.getId(), sortBy.getName()))
                .toList();
    }

    private List<Metadata> findAllBreedSizes() {
        return petSizeRepository.findAll()
                .stream()
                .map(petSize -> new Metadata(petSize.getId(), petSize.getName()))
                .toList();
    }

    public GetReviewsSummaryResponse getReviewsSummary(Long petFoodId) {
        PetFood petFood = petFoodQueryRepository.findPetFoodWithReviewsByPetFoodId(petFoodId);
        List<Review> reviews = petFood.getReviews().getReviews();

        int reviewSize = reviews.size();

        List<SummaryElement> ratingSummary = summarizeRating(reviews, reviewSize);
        RatingSummaryElement rating = new RatingSummaryElement(petFood.calculateRatingAverage(), ratingSummary);
        List<SummaryElement> tastePreference = summarizeTastePreference(reviews, reviewSize);
        List<SummaryElement> stoolCondition = summarizeStoolCondition(reviews, reviewSize);
        List<SummaryElement> adverseReaction = summarizeAdverseReaction(reviews);
        
        return GetReviewsSummaryResponse.of(rating, tastePreference, stoolCondition, adverseReaction);
    }

    private List<SummaryElement> summarizeRating(List<Review> reviews, int reviewSize) {
        //TODO 5, 4, 3, 2, 1 하드 코딩을 없애기 위해 Rating을 Enum으로 리팩터링 할 예정
        //TODO 많은 컨플릭트 예상으로 로지꺼 머지 되면 따로 리팩터링 할 예정
        List<SummaryElement> rating = new ArrayList<>();
        Map<Integer, List<Review>> groupByRating = reviews.stream()
                .collect(Collectors.groupingBy(Review::getRating));

        rating.add(new SummaryElement("5", calculateRankPercent(5, groupByRating) / reviewSize));
        rating.add(new SummaryElement("4", calculateRankPercent(4, groupByRating) / reviewSize));
        rating.add(new SummaryElement("3", calculateRankPercent(3, groupByRating) / reviewSize));
        rating.add(new SummaryElement("2", calculateRankPercent(2, groupByRating) / reviewSize));
        rating.add(new SummaryElement("1", calculateRankPercent(1, groupByRating) / reviewSize));
        return rating;
    }

    private int calculateRankPercent(int rank, Map<Integer, List<Review>> groupByRating) {
        return groupByRating.getOrDefault(rank, new ArrayList<>()).size() * PERCENTAGE;
    }

    private List<SummaryElement> summarizeTastePreference(List<Review> reviews, int reviewSize) {
        List<SummaryElement> tastePreference = new ArrayList<>();
        Map<TastePreference, List<Review>> groupByTastePreference = reviews.stream()
                .collect(Collectors.groupingBy(Review::getTastePreference));

        TastePreference[] values = TastePreference.values();
        for (TastePreference value : values) {
            tastePreference.add(new SummaryElement(value.getDescription(),
                    groupByTastePreference.getOrDefault(value, new ArrayList<>()).size() * PERCENTAGE / reviewSize));
        }
        return tastePreference;
    }

    private List<SummaryElement> summarizeStoolCondition(List<Review> reviews, int reviewSize) {
        List<SummaryElement> stoolCondition = new ArrayList<>();
        Map<StoolCondition, List<Review>> groupByStoolCondition = reviews.stream()
                .collect(Collectors.groupingBy(Review::getStoolCondition));

        StoolCondition[] values = StoolCondition.values();
        for (StoolCondition value : values) {
            stoolCondition.add(new SummaryElement(value.getDescription(),
                    groupByStoolCondition.getOrDefault(value, new ArrayList<>()).size() * PERCENTAGE / reviewSize));
        }
        return stoolCondition;
    }

    private List<SummaryElement> summarizeAdverseReaction(List<Review> reviews) {
        List<SummaryElement> adverseReaction = new ArrayList<>();

        List<AdverseReaction> allAdverseReaction = reviews.stream()
                .map(Review::getAdverseReactions)
                .flatMap(Collection::stream)
                .toList();
        Map<AdverseReactionType, List<AdverseReaction>> groupByAdverseReaction = allAdverseReaction.stream()
                .collect(Collectors.groupingBy(AdverseReaction::getAdverseReactionType));

        AdverseReactionType[] values = AdverseReactionType.values();
        for (AdverseReactionType value : values) {
            adverseReaction.add(new SummaryElement(value.getDescription(),
                    groupByAdverseReaction.getOrDefault(value, new ArrayList<>()).size() * PERCENTAGE / allAdverseReaction.size()));
        }
        return adverseReaction;
    }

}
