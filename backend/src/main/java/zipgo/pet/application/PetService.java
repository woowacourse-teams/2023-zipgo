package zipgo.pet.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.pet.presentation.dto.request.CreatePetRequest;
import zipgo.pet.presentation.dto.request.UpdatePetRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final BreedsRepository breedsRepository;
    private final PetSizeRepository petSizeRepository;

    public Long createPet(Long memberId, CreatePetRequest request) {
        Member owner = memberRepository.getById(memberId);
        PetSize petSize = petSizeRepository.getByName(request.petSize());
        Breeds breeds = breedsRepository.getByNameAndPetSizeId(request.breed(), petSize.getId());

        Pet pet = petRepository.save(request.toEntity(owner, breeds));
        return pet.getId();
    }

    public void updatePet(Long memberId, Long petId, UpdatePetRequest request) {
        Pet pet = petRepository.getById(petId);

        pet.validateOwner(memberId);

        PetSize petSize = petSizeRepository.getByName(request.petSize());
        Breeds breeds = breedsRepository.getByNameAndPetSizeId(request.breed(), petSize.getId());

        update(request, pet, breeds);
    }

    private void update(UpdatePetRequest request, Pet pet, Breeds breeds) {
        pet.updateName(request.name());
        pet.updateImageUrl(request.image());
        pet.updateBreeds(breeds);
        pet.updateBirthYear(request.calculateBirthYear());
        pet.updateWeight(request.weight());
    }

    public List<Breeds> readBreeds() {
        final String excludeName = "믹스견";
        List<Breeds> breeds = breedsRepository.findByNameNotContaining(excludeName);
        Breeds mixedBreeds = Breeds.builder()
                .id(0L)
                .name(excludeName)
                .build();
        breeds.add(mixedBreeds);
        return new ArrayList<>(breeds);
    }

}
