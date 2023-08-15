package zipgo.pet.application;

import java.time.Year;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Gender;
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
        Member member = memberRepository.getById(memberId);
        PetSize petSize = petSizeRepository.getByName(request.petSize());
        Breeds breeds = breedsRepository.getByNameAndPetSize(request.breed(), petSize.getId());

        Pet pet = petRepository.save(toPet(request, member, breeds));
        return pet.getId();
    }

    private Pet toPet(
            CreatePetRequest request,
            Member member,
            Breeds breeds
    ) {
        int birthYear = Year.now().getValue() - request.age();
        return Pet.builder()
                .birthYear(Year.of(birthYear))
                .owner(member)
                .name(request.name())
                .gender(Gender.from(request.gender()))
                .breeds(breeds)
                .weight(request.weight())
                .build();
    }

}


