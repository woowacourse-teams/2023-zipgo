package zipgo.petfood.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.review.domain.Review;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {

    private static final int PERCENTAGE = 100;
    private static final double DEFAULT_PERCENTAGE = 0.0;

    @Default
    @OneToMany(mappedBy = "petFood")
    private List<Review> reviews = new ArrayList<>();

    public double calculateRatingAverage() {
        double average = reviews.stream()
                .mapToInt(review -> review.getRating())
                .average()
                .orElse(DEFAULT_PERCENTAGE);
        return roundToOneDecimal(average);
    }

    private double roundToOneDecimal(double average) {
        return Math.round(average * 10) / 10.0;
    }

    public int getRatingPercentage(int rating) {
        Map<Integer, Long> ratingAndCount = getRatingAndCount();
        long count = ratingAndCount.getOrDefault(rating, 0L);
        return calculateRatingPercentage(reviews.size(), count);
    }

    private Map<Integer, Long> getRatingAndCount() {
        return reviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getRating,
                        Collectors.counting()
                ));
    }

    private int calculateRatingPercentage(int totalReviews, long count) {
        if (totalReviews == 0) {
            return 0;
        }
        return (int) ((count * PERCENTAGE) / totalReviews);
    }

    public int getTastesPercentage(String tastePreference) {
        Map<String, Long> tastesAndCount = getTastesAndCount();
        long count = tastesAndCount.getOrDefault(tastePreference, 0L);
        return TastePreference.getDistributionPercentage(reviews.size(), count);
    }

    private Map<String, Long> getTastesAndCount() {
        return reviews.stream()
                .collect(groupingBy(
                        review -> review.getTastePreference().name(),
                        counting()
                ));
    }

    public int getStoolsConditionPercentage(String stoolCondition) {
        Map<String, Long> stoolConditions = getStoolsAndCount();
        long count = stoolConditions.getOrDefault(stoolCondition, 0L);
        return StoolCondition.getDistributionPercentage(reviews.size(), count);
    }

    private Map<String, Long> getStoolsAndCount() {
        return reviews.stream()
                .collect(groupingBy(
                        review -> review.getStoolCondition().name(),
                        counting()
                ));
    }

    public int getAdverseReactionPercentage(String adverseReactionType) {
        Map<String, Long> adverseReactionsAndCount = getAdverseReactionsAndCount();
        int total = getAdverseReactionCount();
        Long count = adverseReactionsAndCount.getOrDefault(adverseReactionType, 0L);
        return AdverseReactionType.getDistributionPercentage(total, count);
    }

    private Map<String, Long> getAdverseReactionsAndCount() {
        return reviews.stream()
                .flatMap(review -> review.getAdverseReactions().stream())
                .collect(groupingBy(
                        adverseReaction -> adverseReaction.getAdverseReactionType().name(),
                        Collectors.counting()
                ));
    }

    private int getAdverseReactionCount() {
        return reviews.stream()
                .mapToInt(review -> review.getAdverseReactions().size())
                .sum();
    }

    public int countReviews() {
        return reviews.size();
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

}
