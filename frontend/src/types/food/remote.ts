import { Filters, Food, FoodDetail, KeywordEn, KeywordsEn } from './client';

export interface GetFoodListReq extends Record<KeywordEn, string | undefined> {}

export interface GetFoodListRes {
  petFoods: Food[];
}

export interface GetFoodDetailReq {
  petFoodId: string;
}

export interface GetFoodDetailRes extends FoodDetail {}

export interface GetFoodListFilterMetaRes {
  keywords: KeywordsEn;
  filters: Filters;
}
