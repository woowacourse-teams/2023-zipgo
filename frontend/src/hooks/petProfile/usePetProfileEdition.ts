import { ChangeEvent, useEffect, useState } from 'react';

import {
  AGE_GROUP,
  AGE_GROUP_ID,
  PET_AGE_ADULT,
  PET_AGE_MAX,
  PET_AGE_MIN,
} from '@/constants/petProfile';
import { PetProfile, PetSize } from '@/types/petProfile/client';

import useEasyNavigate from '../@common/useEasyNavigate';
import { useValidParams } from '../@common/useValidParams';
import { useEditPetMutation, usePetItemQuery, useRemovePetMutation } from '../query/petProfile';
import { usePetProfileValidation } from './usePetProfileValidation';

interface PetInput extends Omit<PetProfile, 'weight'> {
  weight: number | string;
}

export const usePetProfileEdition = () => {
  const { goHome } = useEasyNavigate();
  const { isValidName, isValidAgeRange, isValidWeight } = usePetProfileValidation();

  const { petId } = useValidParams(['petId']);
  const { petItem } = usePetItemQuery({ petId: Number(petId) });
  const { editPetMutation } = useEditPetMutation();
  const { removePetMutation } = useRemovePetMutation();

  const [pet, setPet] = useState<PetInput | undefined>(petItem);
  const [isValidNameInput, setIsValidNameInput] = useState(true);
  const [isValidAgeSelect, setIsValidAgeSelect] = useState(true);
  const [isValidWeightInput, setIsValidWeightInput] = useState(true);
  const isValidForm = isValidNameInput && isValidAgeSelect && isValidWeightInput;

  useEffect(() => {
    setPet(petItem);
  }, [petItem]);

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    const petName = e.target.value;

    setPet(prev => {
      if (!prev) return prev;

      return { ...prev, name: petName };
    });

    setIsValidNameInput(isValidName(petName));
  };

  const onChangeAge = (e: ChangeEvent<HTMLSelectElement>) => {
    const petAge = Number(e.target.value);

    if (isValidAgeRange(petAge)) {
      setIsValidAgeSelect(true);
      setPet(prev => {
        let ageGroupId: number = AGE_GROUP_ID.ADULT;

        if (!prev) return prev;
        if (petAge <= PET_AGE_MIN) ageGroupId = AGE_GROUP_ID.PUPPY;
        if (petAge > PET_AGE_ADULT && petAge <= PET_AGE_MAX) ageGroupId = AGE_GROUP_ID.SENIOR;

        const ageGroup = AGE_GROUP[ageGroupId - 1];

        return { ...prev, age: petAge, ageGroup, ageGroupId };
      });

      return;
    }

    setIsValidAgeSelect(false);
  };

  const onChangeWeight = (e: ChangeEvent<HTMLInputElement>) => {
    const petWeight = e.target.value;

    setPet(prev => {
      if (!prev) return prev;

      return { ...prev, weight: petWeight };
    });

    setIsValidWeightInput(isValidWeight(petWeight));
  };

  const onChangePetSize = (petSize: PetSize) => {
    setPet(prev => {
      if (!prev) return prev;

      return { ...prev, petSize };
    });
  };

  const onChangeImage = (imageUrl: string) => {
    setPet(prev => {
      if (!prev) return prev;

      return { ...prev, imageUrl };
    });
  };

  const onSubmitNewPetProfile = () => {
    if (pet && isValidForm) {
      const newPetProfile = {
        ...pet,
        weight: Number(pet.weight),
      };

      editPetMutation.editPet(newPetProfile);

      goHome();
    }
  };

  const onClickRemoveButton = (petId: number) => {
    confirm('정말 삭제하시겠어요?') && removePetMutation.removePet({ petId });

    goHome();
  };

  return {
    pet,
    isValidForm,
    isValidNameInput,
    isValidAgeSelect,
    isValidWeightInput,
    onChangeName,
    onChangeAge,
    onChangeWeight,
    onChangePetSize,
    onChangeImage,
    onSubmitNewPetProfile,
    onClickRemoveButton,
  };
};
