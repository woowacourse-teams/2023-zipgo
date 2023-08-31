import { ChangeEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { PATH } from '@/router/routes';
import { PetProfile, PetSize } from '@/types/petProfile/client';

import { useValidParams } from '../@common/useValidParams';
import { useEditPetMutation, usePetItemQuery, useRemovePetMutation } from '../query/petProfile';
import { usePetProfileValidation } from './usePetProfileValidation';

interface PetInput extends Omit<PetProfile, 'weight'> {
  weight: number | string;
}

export const usePetProfileEdition = () => {
  const navigate = useNavigate();
  const { updatePetProfile, resetPetProfile } = usePetProfile();
  const { isValidName, isValidAgeRange, isValidWeight } = usePetProfileValidation();

  const { petId } = useValidParams(['petId']);
  const { petItem, resetPetItemQuery } = usePetItemQuery({ petId: Number(petId) });
  const { editPetMutation } = useEditPetMutation();
  const { removePetMutation } = useRemovePetMutation();

  const [pet, setPet] = useState<PetInput | undefined>(petItem);
  const [isValidNameInput, setIsValidNameInput] = useState(true);
  const [isValidAgeSelect, setIsValidAgeSelect] = useState(true);
  const [isValidWeightInput, setIsValidWeightInput] = useState(true);
  const isValidForm = isValidNameInput && isValidAgeSelect && isValidWeightInput;

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
        if (!prev) return prev;

        return { ...prev, age: petAge };
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

    if (isValidWeight(petWeight)) setIsValidWeightInput(true);
    if (!isValidWeight(petWeight)) setIsValidWeightInput(false);
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

      editPetMutation
        .editPet(newPetProfile)
        .then(() => {
          updatePetProfile(newPetProfile);
          resetPetItemQuery();

          alert('반려동물 정보 수정이 완료되었습니다.');
          navigate(PATH.HOME);
        })
        .catch(() => {
          alert('반려동물 정보 수정에 실패했습니다.');
          navigate(PATH.HOME);
        });

      return;
    }

    alert('올바른 정보를 입력해주세요!');
  };

  const onClickRemoveButton = (petId: number) => {
    confirm('정말 삭제하시겠어요?') &&
      removePetMutation.removePet({ petId }).then(() => {
        const userInfo = JSON.parse(localStorage.getItem('userInfo')!);

        localStorage.setItem('userInfo', JSON.stringify({ ...userInfo, hasPet: false }));

        resetPetProfile();
        resetPetItemQuery();

        alert('반려동물 정보를 삭제했습니다.');
      });

    navigate(PATH.HOME);
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
