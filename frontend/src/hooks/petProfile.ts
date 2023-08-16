import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { PET_PROFILE_ADDITION_STEP, STEP_PATH } from '@/constants/petProfile';
import { routerPath } from '@/router/routes';

export const usePetProfileStep = () => {
  const navigate = useNavigate();

  const [step, setStep] = useState(0);
  const [isValidStep, setIsValidStep] = useState(false);
  const [isMixedBreed, setIsMixedBreed] = useState(false);

  const totalStep = Object.values(PET_PROFILE_ADDITION_STEP).length;
  const isLastStep = step === totalStep;

  const goBack = (): void => navigate(routerPath.back);
  const goNext = () => {
    if (step === PET_PROFILE_ADDITION_STEP.BREED && !isMixedBreed) {
      navigate(STEP_PATH[step + 2]);
      return;
    }

    if (!isLastStep) navigate(STEP_PATH[step + 1]);
  };

  const updateIsMixedBreed = (isMixed: boolean) => setIsMixedBreed(isMixed);
  const updateCurrentStep = (step: number) => setStep(step);
  const updateIsValidStep = (isValid: boolean) => setIsValidStep(isValid);

  return {
    step,
    totalStep,
    isLastStep,
    isValidStep,
    updateIsMixedBreed,
    updateCurrentStep,
    updateIsValidStep,
    goBack,
    goNext,
  };
};
