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

interface PostPetReq
  extends Omit<PetProfile, 'id' | 'petSize' | 'breedId' | 'ageGroup' | 'ageGroupId'> {
  petSize?: PetSize;
}

interface PostPetRes {}

interface PutPetReq extends PetProfile {}

interface PutPetRes {}

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
  PostPetReq,
  PostPetRes,
  PutPetReq,
  PutPetRes,
};
