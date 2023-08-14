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
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.presentation.dto.request.CreatePetRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final BreedsRepository breedsRepository;
    private final ImageClient imageClient;

    public Long createPet(Long memberId, CreatePetRequest request) {
        Member member = memberRepository.getById(memberId);
        Breeds breeds = breedsRepository.getByName(request.breeds());

        int birthYear = Year.now().getValue() - request.age();
        Pet pet = petRepository.save(toPet(request, birthYear, member, breeds));
        String imageUrl = imageClient.upload(String.valueOf(pet.getId()), request.image());
        pet.updateImageUrl(imageUrl);

        return pet.getId();
    }

    private Pet toPet(
            CreatePetRequest request,
            int birthYear,
            Member member,
            Breeds breeds
    ) {
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


