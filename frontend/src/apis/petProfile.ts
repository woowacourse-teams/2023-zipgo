import { GetBreedsRes, PostPetProfileReq, PostPetProfileRes } from '@/types/petProfile/remote';

import { client } from '.';

export const getBreeds = async () => {
  const { data } = await client.get<GetBreedsRes>('/pets/breeds');

  return data;
};

export const postPetProfile = async (postPetProfileProps: PostPetProfileReq) => {
  const { data } = await client.post<PostPetProfileRes>('/pets', postPetProfileProps);

  return data;
};
