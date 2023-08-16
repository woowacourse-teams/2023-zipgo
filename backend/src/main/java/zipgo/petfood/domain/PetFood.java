package zipgo.petfood.domain;

import jakarta.persistence.CascadeType;
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
import zipgo.review.domain.Review;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
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

    @Embedded
    private HasStandard hasStandard;

    @Embedded
    private Reviews reviews;

    @ManyToOne(fetch = LAZY, optional = false)
    private Brand brand;

    @Builder.Default
    @OneToMany(mappedBy = "petFood", cascade = {CascadeType.PERSIST, REMOVE})
    private List<PetFoodPrimaryIngredient> petFoodPrimaryIngredients = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "petFood", cascade = {CascadeType.PERSIST, REMOVE})
    private List<PetFoodFunctionality> petFoodFunctionalities = new ArrayList<>();

    public double calculateRatingAverage() {
        return reviews.calculateRatingAverage();
    }

    public int countReviews() {
        return reviews.countReviews();
    }

    public void addPetFoodFunctionality(PetFoodFunctionality petFoodFunctionality) {
        this.petFoodFunctionalities.add(petFoodFunctionality);
    }

    public void addPetFoodPrimaryIngredient(PetFoodPrimaryIngredient petFoodPrimaryIngredient) {
        this.petFoodPrimaryIngredients.add(petFoodPrimaryIngredient);
    }

    public void addReview(Review review) {
        this.reviews.addReview(review);
    }

}
