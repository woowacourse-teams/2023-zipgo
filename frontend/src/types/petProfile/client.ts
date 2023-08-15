import { GENDERS, PET_SIZES } from '@/constants/petProfile';

import { PostPetProfileReq } from './remote';

type PetProfile = {
  id: number;
  name: string;
  age: number;
  breed: Breed['name'];
  petSize: PetSize;
  gender: Gender;
  weight: number;
  imageUrl: string;
};

type Breed = {
  id: number;
  name: string;
};

type PetSize = (typeof PET_SIZES)[number];

type Gender = (typeof GENDERS)[number];

interface PetProfileOutletContextProps {
  updateIsMixedBreed: (isMixed: boolean) => void;
  updateCurrentStep: (step: number) => void;
  updateIsValidStep: (isValid: boolean) => void;
  updateFinalPetProfile: (petProfile: PostPetProfileReq) => void;
}

export type { Breed, Gender, PetProfile, PetProfileOutletContextProps, PetSize };
