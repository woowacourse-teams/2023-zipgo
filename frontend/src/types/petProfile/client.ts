import { BREEDS_SIZES } from '@/constants/petProfile';

type PetProfile = {
  id: number;
  name: string;
  age: number;
  breeds: Breed['name'];
  breedsSize: BreedsSize;
  gender: '남' | '여';
  weight: number;
  imageUrl: string;
};

type Breed = {
  id: number;
  name: string;
};

type BreedsSize = (typeof BREEDS_SIZES)[number];

interface PetProfileOutletContextProps {
  updateCurrentStep: (step: number) => void;
  updateIsValidStep: (isValid: boolean) => void;
}

export type { Breed, BreedsSize, PetProfile, PetProfileOutletContextProps };
