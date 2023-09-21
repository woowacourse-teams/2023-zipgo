package zipgo.pet.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.application.dto.PetDto;
import zipgo.pet.domain.Breed;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.review.domain.repository.ReviewRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private static final String DEFAULT_PET_IMAGE = "https://image.zipgo.pet/dev/pet-image/dog_icon.svg";

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;
    private final BreedRepository breedRepository;
    private final PetSizeRepository petSizeRepository;
    private final ReviewRepository reviewRepository;

    public Long createPet(Long memberId, PetDto petDto) {
        Member owner = memberRepository.getById(memberId);
        Breed breed = findBreeds(petDto);

        Pet pet = petDto.toEntity(owner, breed);
        updateDefaultImage(pet);

        return petRepository.save(pet).getId();
    }

    private Breed findBreeds(PetDto petDto) {
        if (petDto.petSize() == null || petDto.petSize().isBlank()) {
            return breedRepository.getByName(petDto.breed());
        }
        PetSize petSize = petSizeRepository.getByName(petDto.petSize());
        return breedRepository.getByPetSizeAndName(petSize, petDto.breed());
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

        Breed breed = findBreeds(petDto);
        update(petDto, pet, breed);
    }

    private void update(PetDto petDto, Pet pet, Breed breed) {
        pet.updateName(petDto.name());
        pet.updateImageUrl(petDto.imageUrl());
        updateDefaultImage(pet);
        pet.updateBreeds(breed);
        pet.updateBirthYear(petDto.calculateBirthYear());
        pet.updateWeight(petDto.weight());
    }

    public void deletePet(Long memberId, Long petId) {
        Pet pet = petRepository.getById(petId);
        Member owner = memberRepository.getById(memberId);

        pet.validateOwner(owner);
        reviewRepository.deleteByPet(pet);

        petRepository.delete(pet);
    }

}
