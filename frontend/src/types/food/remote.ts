import { Food } from './client';

export interface GetFoodListReq {
  keyword: string[];
}

export interface GetFoodListRes {
  foodList: Food[];
}
