import { ChangeEvent, useState } from 'react';
import { useOutletContext } from 'react-router-dom';

import { MIXED_BREED, PET_SIZES } from '@/constants/petProfile';
import { usePetAdditionContext } from '@/context/petProfile/PetAdditionContext';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { useAddPetProfileMutation, useBreedListQuery } from '@/hooks/query/petProfile';
import { PetAdditionOutletContextProps, PetProfile, PetSize } from '@/types/petProfile/client';

import useEasyNavigate from '../@common/useEasyNavigate';
import { usePetProfileValidation } from './usePetProfileValidation';

export const usePetProfileAddition = () => {
  const { goHome } = useEasyNavigate();
  const { breedList } = useBreedListQuery();
  const { addPetProfileMutation } = useAddPetProfileMutation();
  const { isValidAgeRange, isValidGender, isValidName, isValidWeight } = usePetProfileValidation();

  const { updatePetProfile: updatePetProfileInHeader } = usePetProfile(); // 에디가 만든 context
  const { petProfile, updatePetProfile } = usePetAdditionContext();
  const { updateIsValidStep } = useOutletContext<PetAdditionOutletContextProps>();

  const [isValidInput, setIsValidInput] = useState(true);

  const onChangeAge = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedAge = Number(e.target.value);

    if (isValidAgeRange(selectedAge)) {
      setIsValidInput(true);
      updateIsValidStep(true);
      updatePetProfile({ age: selectedAge });

      return;
    }

    setIsValidInput(false);
    updateIsValidStep(false);
  };

  const onChangeBreed = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedBreed = e.target.value;

    updateIsValidStep(true);
    setIsValidInput(true);
    updatePetProfile({ breed: selectedBreed });
  };

  const onChangeGender = (e: ChangeEvent<HTMLInputElement>) => {
    const gender = e.target.value;

    if (isValidGender(gender)) {
      updateIsValidStep(true);
      updatePetProfile({ gender });
    }
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    const petName = e.target.value;

    if (isValidName(petName)) {
      setIsValidInput(true);
      updateIsValidStep(true);
      updatePetProfile({ name: petName });

      return;
    }

    setIsValidInput(false);
    updateIsValidStep(false);
  };

  const onClickPetSize = (petSize: PetSize) => {
    updateIsValidStep(true);
    updatePetProfile({ petSize });
  };

  const onChangeWeight = (e: ChangeEvent<HTMLInputElement>) => {
    const petWeight = e.target.value;

    if (isValidWeight(petWeight)) {
      setIsValidInput(true);
      updateIsValidStep(true);
      updatePetProfile({ weight: Number(petWeight) });

      return;
    }

    setIsValidInput(false);
    updateIsValidStep(false);
  };

  const onSubmitPetProfile = () => {
    addPetProfileMutation
      .addPetProfile(petProfile)
      .then(async res => {
        const userInfo = JSON.parse(localStorage.getItem('userInfo')!);

        const userPetBreed = breedList?.find(breed => breed.name === petProfile.breed);
        const petProfileWithId = {
          ...petProfile,
          id: 1,
          petSize: userPetBreed?.name === MIXED_BREED ? petProfile.petSize : PET_SIZES[0],
        } as PetProfile;

        updatePetProfileInHeader(petProfileWithId);

        localStorage.setItem('userInfo', JSON.stringify({ ...userInfo, hasPet: true }));

        alert('반려동물 정보 등록이 완료되었습니다.');
      })
      .catch(error => {
        alert('반려동물 정보 등록에 실패했습니다.');
      });

    goHome();
  };

  return {
    isValidInput,
    petProfile,
    updateIsValidStep,
    onChangeAge,
    onChangeBreed,
    onChangeGender,
    onChangeName,
    onClickPetSize,
    onChangeWeight,
    onSubmitPetProfile,
  };
};
