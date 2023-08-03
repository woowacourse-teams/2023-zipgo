import {
  DeleteReviewReq,
  DeleteReviewRes,
  GetReviewsReq,
  GetReviewsRes,
  PostReviewReq,
  PostReviewRes,
  PutReviewReq,
  PutReviewRes,
} from '@/types/review/remote';

import { client } from '.';

export const getReviews = async ({ petFoodId }: GetReviewsReq) => {
  const { data } = await client.get<GetReviewsRes>(`/pet-foods/${petFoodId}/reviews`);

  return data;
};

export const postReview = async (postReviewProps: PostReviewReq) => {
  const { data } = await client.post<PostReviewRes>('/reviews', postReviewProps);

  return data;
};

export const putReview = async (putReviewProps: PutReviewReq) => {
  const { reviewId, ...restPutReviewProps } = putReviewProps;
  const { data } = await client.put<PutReviewRes>(`/reviews/${reviewId}`, restPutReviewProps);

  return data;
};

export const deleteReview = async ({ reviewId }: DeleteReviewReq) => {
  const { data } = await client.delete<DeleteReviewRes>(`/reviews/${reviewId}`);

  return data;
};
