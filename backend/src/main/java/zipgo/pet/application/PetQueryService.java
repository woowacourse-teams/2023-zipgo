package zipgo.pet.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breed;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.repository.BreedRepository;
import zipgo.pet.domain.repository.PetRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetQueryService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final BreedRepository breedRepository;

    public List<Pet> readMemberPets(Long memberId) {
        Member owner = memberRepository.getById(memberId);
        return petRepository.findByOwner(owner);
    }

    public Pet readPet(Long petId) {
        return petRepository.getById(petId);
    }

    public List<Breed> readBreeds() {
        Breeds breeds = Breeds.from(breedRepository.findAll());
        return breeds.getValues();
    }

}
