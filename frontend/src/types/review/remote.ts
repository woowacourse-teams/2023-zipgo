import {
  AdverseReaction,
  AlignControlsMeta,
  FilterControlsMeta,
  Review,
  StoolCondition,
  TastePreference,
} from './client';

interface GetReviewReq {
  reviewId: number;
}

interface GetReviewRes extends Review {}

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

interface PutReviewReq extends PostReviewReq {
  reviewId: number;
}

interface PutReviewRes {}

interface DeleteReviewReq {
  reviewId: number;
}

interface DeleteReviewRes {}

interface GetReviewsMetaRes {
  petSizes: FilterControlsMeta;
  sortBy: AlignControlsMeta;
  ageGroups: FilterControlsMeta;
  breeds: FilterControlsMeta;
}

export type {
  DeleteReviewReq,
  DeleteReviewRes,
  GetReviewReq,
  GetReviewRes,
  GetReviewsMetaRes,
  GetReviewsReq,
  GetReviewsRes,
  PostReviewReq,
  PostReviewRes,
  PutReviewReq,
  PutReviewRes,
};
