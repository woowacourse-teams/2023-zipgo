import { GetReviewsReq, GetReviewsRes, PostReviewReq, PostReviewRes } from '@/types/review/remote';

import { client } from '.';

export const getReviews = async ({ petFoodId }: GetReviewsReq) => {
  const { data } = await client.get<GetReviewsRes>(`/pet-foods/${petFoodId}/reviews`);

  return data;
};

export const postReview = async (postReviewProps: PostReviewReq) => {
  const { data } = await client.post<PostReviewRes>('/reviews', postReviewProps);

  return data;
};
