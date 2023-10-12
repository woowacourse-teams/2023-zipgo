import { ChangeEvent, useState } from 'react';

import { PET_SIZES } from '@/constants/petProfile';
import { usePetAdditionContext } from '@/context/petProfile/PetAdditionContext';
import { useAddPetMutation } from '@/hooks/query/petProfile';
import { PetSize } from '@/types/petProfile/client';

import useEasyNavigate from '../@common/useEasyNavigate';
import { usePetProfileValidation } from './usePetProfileValidation';

export const usePetProfileAddition = () => {
  const { goHome } = useEasyNavigate();
  const { addPetMutation } = useAddPetMutation();
  const { isValidAgeRange, isValidGender, isValidName, isValidWeight, isMixedBreed } =
    usePetProfileValidation();

  const { petProfile, updatePetProfile } = usePetAdditionContext();

  const [isValidInput, setIsValidInput] = useState(false);
  const [isFirstRendered, setIsFirstRendered] = useState(true);

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    const petName = e.target.value;

    setIsFirstRendered(false);
    updatePetProfile({ name: petName });

    setIsValidInput(isValidName(petName));
  };

  const onChangeAge = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedAge = Number(e.target.value);

    if (isValidAgeRange(selectedAge)) {
      setIsValidInput(true);
      updatePetProfile({ age: selectedAge });

      return;
    }

    setIsValidInput(false);
  };

  const onChangeBreed = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedBreed = e.target.value;

    setIsValidInput(true);

    if (isMixedBreed(selectedBreed)) {
      updatePetProfile({ breed: selectedBreed, petSize: PET_SIZES[0] });

      return;
    }

    updatePetProfile({ breed: selectedBreed });
  };

  const onClickPetSize = (petSize: PetSize) => {
    updatePetProfile({ petSize });
  };

  const onChangeGender = (e: ChangeEvent<HTMLInputElement>) => {
    const gender = e.target.value;

    if (isValidGender(gender)) {
      updatePetProfile({ gender });
    }
  };

  const onChangeWeight = (e: ChangeEvent<HTMLInputElement>) => {
    const petWeight = e.target.value;

    setIsFirstRendered(false);
    updatePetProfile({ weight: petWeight });

    setIsValidInput(isValidWeight(petWeight));
  };

  const onSubmitPetProfile = () => {
    addPetMutation.addPet({ ...petProfile, weight: Number(petProfile.weight) });

    goHome();
  };

  return {
    isFirstRendered,
    isValidInput,
    petProfile,
    setIsValidInput,
    onChangeAge,
    onChangeBreed,
    onChangeGender,
    onChangeName,
    onClickPetSize,
    onChangeWeight,
    onSubmitPetProfile,
  };
};
