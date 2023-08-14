interface PetProfileOutletContextProps {
  petName: string;
  updatePetName: (name: string) => void;
  updateCurrentStep: (step: number) => void;
  updateIsValidStep: (isValid: boolean) => void;
}

export type { PetProfileOutletContextProps };
