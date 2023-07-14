package zipgo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
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

    public PetFood(final String name, final String purchaseLink, final String imageUrl, final Keyword keyword) {
        this.name = name;
        this.purchaseLink = purchaseLink;
        this.imageUrl = imageUrl;
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetFood petFood = (PetFood) o;
        return Objects.equals(id, petFood.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
