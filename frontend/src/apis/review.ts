import { GetReviewsReq, GetReviewsRes } from '@/types/review/remote';

import { client } from '.';

export const getReviews = async ({ foodId }: GetReviewsReq) => {
  const { data } = await client.get<GetReviewsRes>(`/pet-foods/${foodId}/reviews`);

  return data;
};
