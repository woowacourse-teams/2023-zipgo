package zipgo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@AllArgsConstructor
public class PetFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String purchaseLink;
    private String imageUrl;

    @ManyToOne
    private Keyword keyword;

    public PetFood() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PetFood petFood = (PetFood) o;
        return Objects.equals(id, petFood.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
