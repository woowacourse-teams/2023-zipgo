import { AGE_GROUP, GENDERS, PET_SIZES } from '@/constants/petProfile';

type PetProfile = {
  id: number;
  name: string;
  age: number;
  breed: Breed['name'];
  breedId: Breed['id'];
  petSize: PetSize;
  gender: Gender;
  weight: number;
  imageUrl: string;
  ageGroup: AgeGroup;
  ageGroupId: number;
};

type Breed = {
  id: number;
  name: string;
};

type AgeGroup = (typeof AGE_GROUP)[number];

type PetSize = (typeof PET_SIZES)[number];

type Gender = (typeof GENDERS)[number];

interface PetAdditionOutletContextProps {
  updateCurrentStep: (step: number) => void;
  updateIsValidStep: (isValid: boolean) => void;
}

export type { Breed, Gender, PetAdditionOutletContextProps, PetProfile, PetSize };
