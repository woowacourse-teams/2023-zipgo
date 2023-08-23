import { ChangeEvent, useState } from 'react';

import { FEMALE, MALE, MIXED_BREED, PET_AGE_MAX, PET_AGE_MIN } from '@/constants/petProfile';
import { Gender } from '@/types/petProfile/client';

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
