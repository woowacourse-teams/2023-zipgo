import { useState } from 'react';

import { PET_PROFILE_ADDITION_STEP, STEP_PATH } from '@/constants/petProfile';

export const usePetProfileStep = () => {
  const [step, setStep] = useState(0);
  const [isValidStep, setIsValidStep] = useState(false);
  const [isMixedBreed, setIsMixedBreed] = useState(false);

  const totalStep = Object.values(PET_PROFILE_ADDITION_STEP).length;
  const isLastStep = step === totalStep;

  const updateIsMixedBreed = (isMixed: boolean) => setIsMixedBreed(isMixed);
  const updateCurrentStep = (step: number) => setStep(step);
  const updateIsValidStep = (isValid: boolean) => setIsValidStep(isValid);

  return {
    step,
    totalStep,
    isLastStep,
    isValidStep,
    isMixedBreed,
    updateIsMixedBreed,
    updateCurrentStep,
    updateIsValidStep,
  };
};
