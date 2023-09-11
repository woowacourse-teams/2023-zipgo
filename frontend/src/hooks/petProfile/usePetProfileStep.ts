import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { PET_PROFILE_ADDITION_STEP, STEP_PATH } from '@/constants/petProfile';
import { usePetAdditionContext } from '@/context/petProfile/PetAdditionContext';
import { routerPath } from '@/router/routes';

import { usePetProfileValidation } from './usePetProfileValidation';

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
