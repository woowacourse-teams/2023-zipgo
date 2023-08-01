import { AdverseReaction, Review, StoolCondition, TastePreference } from './client';

interface GetReviewsReq {
  petFoodId: string;
}

interface GetReviewsRes {
  reviews: Review[];
}

interface PostReviewReq {
  petFoodId: number;
  rating: number;
  comment: string;
  tastePreference: TastePreference;
  stoolCondition: StoolCondition;
  adverseReactions: AdverseReaction[];
}

interface PostReviewRes {}

interface DeleteReviewReq {
  reviewId: number;
}

interface DeleteReviewRes {}

export type {
  DeleteReviewReq,
  DeleteReviewRes,
  GetReviewsReq,
  GetReviewsRes,
  PostReviewReq,
  PostReviewRes,
};
