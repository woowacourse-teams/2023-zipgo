package zipgo.petfood.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.brand.domain.Brand;
import zipgo.common.entity.BaseTimeEntity;
import zipgo.petfood.domain.type.PetFoodOption;
import zipgo.review.domain.Review;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetFood extends BaseTimeEntity {

    @Id
    @Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2048)
    private String purchaseLink;

    @Column(length = 2048)
    private String imageUrl;

    @ManyToOne(fetch = LAZY, optional = false)
    private Brand brand;

    @Embedded
    private HasStandard hasStandard;

    @Embedded
    private Reviews reviews;

    @Builder.Default
    @OneToMany(mappedBy = "petFood", orphanRemoval = true, cascade = {PERSIST, REMOVE})
    private List<PetFoodEffect> petFoodEffects = new ArrayList<>();

    public double calculateRatingAverage() {
        return reviews.calculateRatingAverage();
    }

    public int countReviews() {
        return reviews.countReviews();
    }

    public void addReview(Review review) {
        this.reviews.addReview(review);
    }

    public List<String> getPetFoodEffectsBy(PetFoodOption petFoodOption) {
        return petFoodEffects.stream()
                .filter(petFoodEffect -> petFoodEffect.isEqualTo(petFoodOption))
                .map(petFoodEffect -> petFoodEffect.getDescription())
                .toList();
    }

    public void addPetFoodEffect(PetFoodEffect petFoodEffect) {
        this.petFoodEffects.add(petFoodEffect);
    }

}
