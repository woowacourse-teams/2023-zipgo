import { Review } from './client';

interface GetReviewsReq {
  petFoodId: string;
}

interface GetReviewsRes {
  reviews: Review[];
}

export type { GetReviewsReq, GetReviewsRes };
