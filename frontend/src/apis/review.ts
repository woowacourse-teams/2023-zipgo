import { GetReviewsReq, GetReviewsRes } from '@/types/review/remote';

import { client } from '.';

export const getReviews = async ({ petFoodId }: GetReviewsReq) => {
  const { data } = await client.get<GetReviewsRes>(`/pet-foods/${petFoodId}/reviews`);

  return data;
};
