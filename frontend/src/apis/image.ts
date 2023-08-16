import { PostImageFileReq, PostImageFileRes } from '@/types/image/remote';

import { client } from '.';

export const postImageFile = async ({ imageFile }: PostImageFileReq) => {
  const { data } = await client.post<PostImageFileRes>('/images', imageFile);

  return data;
};
