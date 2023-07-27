import { Review } from './client';

export interface GetReviewsReq {
  foodId: number;
}

export interface GetReviewsRes {
  reviews: Review[];
}
