import {
  DeletePetReq,
  DeletePetRes,
  GetBreedsRes,
  GetPetReq,
  GetPetRes,
  GetPetsRes,
  PostPetProfileReq,
  PostPetProfileRes,
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

export const postPetProfile = async (postPetProfileProps: PostPetProfileReq) => {
  const { data } = await client.post<PostPetProfileRes>('/pets', postPetProfileProps);

  return data;
};

export const deletePet = async ({ petId }: DeletePetReq) => {
  const { data } = await client.delete<DeletePetRes>(`/pets/${petId}`);

  return data;
};
