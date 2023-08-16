package zipgo.pet.application;

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

    public List<Breeds> readBreeds() {
         return breedsRepository.findAll();
    }

}
