import {
  GetFoodDetailRes,
  GetFoodListFilterMetaRes,
  GetFoodListReq,
  GetFoodListRes,
} from '@/types/food/remote';

import { GetFoodDetailReq } from '../types/food/remote';
import { client } from '.';

export const getFoodList = async (payload: GetFoodListReq) => {
  const { data } = await client.get<GetFoodListRes>('/pet-foods', {
    params: payload,
  });

  return data;
};

export const getFoodDetail = async ({ petFoodId }: GetFoodDetailReq) => {
  const { data } = await client.get<GetFoodDetailRes>(`/pet-foods/${petFoodId}`);

  return data;
};

export const getFoodListFilterMeta = async () => {
  const { data } = await client.get<GetFoodListFilterMetaRes>('/pet-foods/filters');

  return data;
};
