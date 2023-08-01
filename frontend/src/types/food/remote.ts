import { Food, FoodDetail } from './client';

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
