package zipgo.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.common.entity.BaseTimeEntity;
import zipgo.member.exception.PetAlreadyRegisteredException;
import zipgo.pet.domain.Pet;

import static lombok.AccessLevel.PROTECTED;
import static lombok.EqualsAndHashCode.Include;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Member extends BaseTimeEntity {

    @Id
    @Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String profileImgUrl;

    @OneToOne(mappedBy = "owner")
    private Pet pet;

    public boolean hasPet() {
        return pet != null;
    }

    public void addPet(Pet pet) {
        if (hasPet()) {
            throw new PetAlreadyRegisteredException();
        }
        this.pet = pet;
    }

}
