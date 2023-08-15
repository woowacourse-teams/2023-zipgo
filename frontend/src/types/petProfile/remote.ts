import { Breed, PetProfile } from './client';

interface GetBreedsRes {
  breeds: Breed[];
}

interface PostPetProfileReq extends Omit<PetProfile, 'id' | 'imageUrl'> {
  imageFile?: File;
}

interface PostPetProfileRes {}

export type { GetBreedsRes, PostPetProfileReq, PostPetProfileRes };
