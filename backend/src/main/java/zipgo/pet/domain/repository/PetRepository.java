package zipgo.pet.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Pet;
import zipgo.pet.exception.PetNotFoundException;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findById(Long petId);

    default Pet getById(Long petId) {
        return findById(petId)
                .orElseThrow(PetNotFoundException::new);
    }

    List<Pet> findByOwner(Member owner);

}
