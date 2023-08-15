import { PostImageFileReq, PostImageFileRes } from '@/types/image/remote';

import { client } from '.';

export const postImageFile = async (postImageFileProps: PostImageFileReq) => {
  const { data } = await client.post<PostImageFileRes>('/images', postImageFileProps);

  return data;
};
