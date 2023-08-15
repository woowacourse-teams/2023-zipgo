import { Breed, PetProfile, PetSize } from './client';

interface GetBreedsRes {
  breeds: Breed[];
}

interface GetPetReq {
  petId: number;
}

interface GetPetRes extends PetProfile {}

interface GetPetsReq {}

interface GetPetsRes {
  pets: PetProfile[];
}

interface PostPetProfileReq extends Omit<PetProfile, 'id' | 'petSize'> {
  petSize?: PetSize;
}

interface PostPetProfileRes {}

interface DeletePetReq extends GetPetReq {}

interface DeletePetRes {}

export type {
  DeletePetReq,
  DeletePetRes,
  GetBreedsRes,
  GetPetReq,
  GetPetRes,
  GetPetsReq,
  GetPetsRes,
  PostPetProfileReq,
  PostPetProfileRes,
};
