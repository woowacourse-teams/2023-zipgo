import {
  DeleteHelpfulReactionsReq,
  DeleteHelpfulReactionsRes,
  DeleteReviewReq,
  DeleteReviewRes,
  GetReviewReq,
  GetReviewRes,
  GetReviewsMetaRes,
  GetReviewsReq,
  GetReviewsRes,
  GetReviewSummaryReq,
  GetReviewSummaryRes,
  PostHelpfulReactionsReq,
  PostHelpfulReactionsRes,
  PostReviewReq,
  PostReviewRes,
  PutReviewReq,
  PutReviewRes,
} from '@/types/review/remote';

import { client } from '.';

export const getReview = async ({ reviewId }: GetReviewReq) => {
  const { data } = await client.get<GetReviewRes>(`/reviews/${reviewId}`);

  return data;
};

export const getReviews = async (payload: GetReviewsReq) => {
  const { data } = await client.get<GetReviewsRes>('/reviews', {
    params: payload,
  });

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

export const getReviewSummary = async ({ petFoodId }: GetReviewSummaryReq) => {
  const { data } = await client<GetReviewSummaryRes>('/reviews/summary', {
    params: {
      petFoodId,
    },
  });

  return data;
};

export const getReviewsMeta = async () => {
  const { data } = await client.get<GetReviewsMetaRes>('/reviews/metadata');

  return data;
};

export const postHelpfulReactions = async ({ reviewId }: PostHelpfulReactionsReq) => {
  const { data } = await client.post<PostHelpfulReactionsRes>(
    `/reviews/${reviewId}/helpfulReactions`,
  );

  return data;
};

export const deleteHelpfulReactions = async ({ reviewId }: DeleteHelpfulReactionsReq) => {
  const { data } = await client.delete<DeleteHelpfulReactionsRes>(
    `/reviews/${reviewId}/helpfulReactions`,
  );

  return data;
};
