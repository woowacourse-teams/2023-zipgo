import { BREEDS_SIZES, GENDERS } from '@/constants/petProfile';

type PetProfile = {
  id: number;
  name: string;
  age: number;
  breeds: Breed['name'];
  breedsSize: BreedsSize;
  gender: Gender;
  weight: number;
  imageUrl: string;
};

type Breed = {
  id: number;
  name: string;
};

type BreedsSize = (typeof BREEDS_SIZES)[number];

type Gender = (typeof GENDERS)[number];

interface PetProfileOutletContextProps {
  updateCurrentStep: (step: number) => void;
  updateIsValidStep: (isValid: boolean) => void;
}

export type { Breed, BreedsSize, Gender, PetProfile, PetProfileOutletContextProps };
