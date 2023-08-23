import { ChangeEvent, useState } from 'react';
import { useNavigate, useOutletContext } from 'react-router-dom';

import {
  FEMALE,
  MALE,
  MIXED_BREED,
  PET_AGE_MAX,
  PET_AGE_MIN,
  PET_PROFILE_ADDITION_STEP,
  PET_SIZES,
  STEP_PATH,
} from '@/constants/petProfile';
import { usePetAdditionContext } from '@/context/petProfile/PetAdditionContext';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { routerPath } from '@/router/routes';
import {
  Gender,
  PetAdditionOutletContextProps,
  PetProfile,
  PetSize,
} from '@/types/petProfile/client';

import { useAddPetProfileMutation, useBreedListQuery } from './query/petProfile';

export const usePetProfileStep = () => {
  const navigate = useNavigate();
  const { isMixedBreed } = usePetProfileValidation();
  const { petProfile } = usePetAdditionContext();

  const [step, setStep] = useState(0);
  const [isValidStep, setIsValidStep] = useState(false);

  const totalStep = Object.values(PET_PROFILE_ADDITION_STEP).length;
  const isLastStep = step === totalStep;

  const goBack = (): void => navigate(routerPath.back);
  const goNext = () => {
    if (step === PET_PROFILE_ADDITION_STEP.BREED && !isMixedBreed(petProfile.breed)) {
      navigate(STEP_PATH[step + 2]);
      return;
    }

    if (!isLastStep) navigate(STEP_PATH[step + 1]);
  };

  const updateCurrentStep = (step: number) => setStep(step);
  const updateIsValidStep = (isValid: boolean) => setIsValidStep(isValid);

  return {
    step,
    totalStep,
    isValidStep,
    isLastStep,
    updateCurrentStep,
    updateIsValidStep,
    goBack,
    goNext,
  };
};

export const usePetProfileValidation = () => {
  const [isValidNameInput, setIsValidNameInput] = useState(true);
  const [isValidAgeSelect, setIsValidAgeSelect] = useState(true);
  const [isValidWeightInput, setIsValidWeightInput] = useState(true);

  const validateName = (e: ChangeEvent<HTMLInputElement>) => {
    const petName = e.target.value;

    if (isValidName(petName)) {
      setIsValidNameInput(true);
      return true;
    }

    setIsValidNameInput(false);
    return false;
  };

  const validateAge = (e: ChangeEvent<HTMLSelectElement>) => {
    const petAge = Number(e.target.value);

    if (isValidAgeRange(petAge)) {
      setIsValidAgeSelect(true);
      setIsValidWeightInput(true);
      return true;
    }

    setIsValidAgeSelect(false);
    return false;
  };

  const validateWeight = (e: ChangeEvent<HTMLInputElement>) => {
    const petWeight = e.target.value;

    if (isValidWeight(petWeight)) {
      setIsValidWeightInput(true);
      return true;
    }

    setIsValidWeightInput(false);
    return false;
  };

  const isValidName = (name: string) => {
    const validNameCharacters = /^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{1,10}$/;

    return validNameCharacters.test(name);
  };

  const isValidAgeRange = (age: number) =>
    typeof age === 'number' && age >= PET_AGE_MIN && age <= PET_AGE_MAX;

  const isValidGender = (gender: string): gender is Gender => gender === MALE || gender === FEMALE;

  const isValidWeight = (weight: string) => {
    const validWeightCharacters = /^(?:100(?:\.0)?|\d{1,2}(?:\.\d)?)$/; // 100.0 또는 1~2자리 숫자.소수 첫째짜리 숫자

    if (Number(weight) <= 0) return false;

    return validWeightCharacters.test(weight);
  };

  const isMixedBreed = (breed: string) => breed === MIXED_BREED;

  return {
    isValidNameInput,
    isValidAgeSelect,
    isValidWeightInput,

    validateName,
    validateAge,
    validateWeight,
    isValidName,
    isValidAgeRange,
    isValidGender,
    isValidWeight,
    isMixedBreed,
  };
};

export const usePetProfileAddition = () => {
  const navigate = useNavigate();
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

    navigate(routerPath.home());
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
