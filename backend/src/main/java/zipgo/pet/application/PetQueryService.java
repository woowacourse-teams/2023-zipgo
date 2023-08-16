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
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetQueryService {

    private static final int FIRST_PLACE = 0;

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final BreedsRepository breedsRepository;

    public List<Pet> readMemberPets(Long memberId) {
        Member owner = memberRepository.getById(memberId);
        return petRepository.findByOwner(owner);
    }

    public List<Breeds> readBreeds() {
        String excludeName = "믹스견";
        List<Breeds> breeds = breedsRepository.findByNameNotContaining(excludeName);

        Breeds mixedBreeds = Breeds.builder()
                .id(0L)
                .name(excludeName)
                .build();
        breeds.add(FIRST_PLACE, mixedBreeds);
        return new ArrayList<>(breeds);
    }

}
