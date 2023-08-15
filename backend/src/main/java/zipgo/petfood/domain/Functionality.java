package zipgo.petfood.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.common.entity.BaseTimeEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Functionality extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "functionality")
    private List<PetFoodFunctionality> petFoodFunctionality = new ArrayList<>();

    public void addPetFoodFunctionality(PetFoodFunctionality petFoodFunctionality) {
        this.petFoodFunctionality.add(petFoodFunctionality);
    }

}
