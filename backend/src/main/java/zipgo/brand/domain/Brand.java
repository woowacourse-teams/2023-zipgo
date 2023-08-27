package zipgo.brand.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.common.entity.BaseTimeEntity;
import zipgo.petfood.domain.PetFood;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Brand extends BaseTimeEntity {

    @Id
    @Include
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 2048)
    private String imageUrl;

    @Column(nullable = false)
    private String nation;

    @Column
    private Integer foundedYear;

    @Column(nullable = false)
    private boolean hasResearchCenter;

    @Column(nullable = false)
    private boolean hasResidentVet;

    @Builder.Default
    @OneToMany(mappedBy = "brand")
    private List<PetFood> petFoods = new ArrayList<>();

}
