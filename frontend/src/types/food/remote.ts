import { Food } from './client';

export interface GetFoodListReq {
  keyword: string[];
}

export interface GetFoodListRes {
  petFoods: Pick<Food, 'id' | 'name' | 'imageUrl' | 'purchaseUrl'>[];
}

export interface GetFoodDetailReq {
  petFoodId: string;
}

export interface GetFoodDetailRes extends Food {}
