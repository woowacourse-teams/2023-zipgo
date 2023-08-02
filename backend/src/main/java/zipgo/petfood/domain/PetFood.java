package zipgo.petfood.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.brand.domain.Brand;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetFood {

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Keyword keyword;

    @ManyToOne(fetch = LAZY, optional = false)
    private Brand brand;

    @Embedded
    private PrimaryIngredients primaryIngredients;

    @Embedded
    private HasStandard hasStandard;

    @Embedded
    private Functionality functionality;

    @Embedded
    private Reviews reviews;

    public double calculateRatingAverage() {
        return reviews.calculateRatingAverage();
    }

    public int countReviews() {
        return reviews.countReviews();
    }

    public List<String> getPrimaryIngredients() {
        return primaryIngredients.getPrimaryIngredients();
    }

    public List<String> getFunctionality() {
        return functionality.getFunctionality();
    }

}
