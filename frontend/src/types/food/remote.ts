import { Filters, Food, FoodDetail, KeywordEn, KeywordForPaging, KeywordsEn } from './client';

export interface GetFoodListReq extends Partial<Record<KeywordEn | KeywordForPaging, string>> {}

export interface GetFoodListRes {
  totalCount: number;
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
