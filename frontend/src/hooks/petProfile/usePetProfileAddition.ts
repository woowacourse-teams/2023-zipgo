import { ChangeEvent, useState } from 'react';
import { useOutletContext } from 'react-router-dom';

import { usePetAdditionContext } from '@/context/petProfile/PetAdditionContext';
import { useAddPetProfileMutation } from '@/hooks/query/petProfile';
import { PetAdditionOutletContextProps, PetSize } from '@/types/petProfile/client';

import useEasyNavigate from '../@common/useEasyNavigate';
import { usePetProfileValidation } from './usePetProfileValidation';

export const usePetProfileAddition = () => {
  const { goHome } = useEasyNavigate();
  const { addPetProfileMutation } = useAddPetProfileMutation();
  const { isValidAgeRange, isValidGender, isValidName, isValidWeight } = usePetProfileValidation();

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
    addPetProfileMutation.addPetProfile(petProfile);

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
