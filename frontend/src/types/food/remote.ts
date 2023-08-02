import { Filters, Food, FoodDetail, KeywordsEn } from './client';

export interface GetFoodListReq {
  keyword: string[];
}

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
