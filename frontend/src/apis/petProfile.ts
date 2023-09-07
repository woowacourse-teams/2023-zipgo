import {
  DeletePetReq,
  DeletePetRes,
  GetBreedsRes,
  GetPetReq,
  GetPetRes,
  GetPetsRes,
  PostPetReq,
  PostPetRes,
  PutPetReq,
  PutPetRes,
} from '@/types/petProfile/remote';

import { client } from '.';

export const getBreeds = async () => {
  const { data } = await client.get<GetBreedsRes>('/pets/breeds');

  return data;
};

export const getPet = async ({ petId }: GetPetReq) => {
  const { data } = await client.get<GetPetRes>(`/pets/${petId}`);

  return data;
};

export const getPets = async () => {
  const { data } = await client.get<GetPetsRes>('/pets');

  return data;
};

export const postPet = async (postPetProps: PostPetReq) => {
  const { data } = await client.post<PostPetRes>('/pets', postPetProps);

  return data;
};

export const putPet = async (putPetProps: PutPetReq) => {
  const { id, ...restPetProfile } = putPetProps;
  const { data } = await client.put<PutPetRes>(`/pets/${id}`, restPetProfile);

  return data;
};

export const deletePet = async ({ petId }: DeletePetReq) => {
  const { data } = await client.delete<DeletePetRes>(`/pets/${petId}`);

  return data;
};
