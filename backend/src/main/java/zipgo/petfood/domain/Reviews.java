package zipgo.petfood.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.review.domain.Review;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {

    @OneToMany(mappedBy = "petFood")
    private List<Review> reviews = new ArrayList<>();

    public double calculateRatingAverage() {
        return reviews.stream()
                .mapToInt(review -> review.getRating())
                .average()
                .orElse(0);
    }

    public int countReviews() {
        return reviews.size();
    }

}
