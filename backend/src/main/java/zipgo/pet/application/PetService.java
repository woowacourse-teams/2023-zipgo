package zipgo.pet.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.application.dto.PetDto;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private static final String DEFAULT_PET_IMAGE = "https://image.zipgo.pet/dev/pet-image/dog_icon.svg";

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final BreedsRepository breedsRepository;
    private final PetSizeRepository petSizeRepository;

    public Long createPet(Long memberId, PetDto petDto) {
        Member owner = memberRepository.getById(memberId);
        Breeds breeds = findBreeds(petDto);

        Pet pet = petDto.toEntity(owner, breeds);
        updateDefaultImage(pet);

        return petRepository.save(pet).getId();
    }

    private Breeds findBreeds(PetDto petDto) {
        if (petDto.petSize() == null) {
            return breedsRepository.getByName(petDto.breed());
        }
        PetSize petSize = petSizeRepository.getByName(petDto.petSize());
        return breedsRepository.getByPetSizeAndName(petSize, petDto.breed());
    }

    private void updateDefaultImage(Pet pet) {
        if ("".equals(pet.getImageUrl())) {
            pet.updateImageUrl(DEFAULT_PET_IMAGE);
        }
    }

    public void updatePet(Long memberId, Long petId, PetDto petDto) {
        Pet pet = petRepository.getById(petId);
        Member owner = memberRepository.getById(memberId);

        pet.validateOwner(owner);

        Breeds breeds = findBreeds(petDto);
        update(petDto, pet, breeds);
    }

    private void update(PetDto petDto, Pet pet, Breeds breeds) {
        pet.updateName(petDto.name());
        pet.updateImageUrl(petDto.imageUrl());
        updateDefaultImage(pet);
        pet.updateBreeds(breeds);
        pet.updateBirthYear(petDto.calculateBirthYear());
        pet.updateWeight(petDto.weight());
    }

    public void deletePet(Long memberId, Long petId) {
        Pet pet = petRepository.getById(petId);
        Member owner = memberRepository.getById(memberId);

        pet.validateOwner(owner);

        petRepository.delete(pet);
    }

}
