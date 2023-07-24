package zipgo.petfood.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PetFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Long id;

    private String name;

    private String purchaseLink;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Keyword keyword;

    public PetFood(String name, String purchaseLink, String imageUrl) {
        this.name = name;
        this.purchaseLink = purchaseLink;
        this.imageUrl = imageUrl;
    }

    public PetFood(String name, String purchaseLink, String imageUrl, Keyword keyword) {
        this.name = name;
        this.purchaseLink = purchaseLink;
        this.imageUrl = imageUrl;
        this.keyword = keyword;
    }

}
