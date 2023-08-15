import { Breed, PetProfile, PetSize } from './client';

interface GetBreedsRes {
  breeds: Breed[];
}

interface PostPetProfileReq extends Omit<PetProfile, 'id' | 'petSize'> {
  petSize?: PetSize;
}

interface PostPetProfileRes {}

export type { GetBreedsRes, PostPetProfileReq, PostPetProfileRes };
