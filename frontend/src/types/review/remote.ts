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

interface GetReviewsMetaRes {
  petSizes: FilterControlsMeta;
  sortBy: AlignControlsMeta;
  ageGroups: FilterControlsMeta;
  breeds: FilterControlsMeta;
}

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
