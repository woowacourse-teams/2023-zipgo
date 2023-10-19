import {
  AdverseReaction,
  AlignControlsMeta,
  ChartInfo,
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
  petSizeId?: string;
  ageGroupId?: string;
  breedId?: string;
  sortById?: string;
  size?: number;
  lastReviewId?: number;
}

interface GetReviewsRes {
  reviews: Review[];
}

interface PostReviewReq {
  petId: number;
  petFoodId: string;
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
  petFoodId: string;
  reviewId: number;
}

interface DeleteReviewRes {}

interface GetReviewSummaryReq {
  petFoodId: string;
}

interface GetReviewSummaryRes {
  rating: { average: number; rating: ChartInfo };
  tastePreference: ChartInfo;
  stoolCondition: ChartInfo;
  adverseReaction: ChartInfo;
}

type GetReviewsMetaRes = FilterControlsMeta & AlignControlsMeta;

interface PostHelpfulReactionsReq {
  petFoodId: string;
  reviewId: number;
}

interface PostHelpfulReactionsRes {}

interface DeleteHelpfulReactionsReq {
  petFoodId: string;
  reviewId: number;
}

interface DeleteHelpfulReactionsRes {}

export type {
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
};
