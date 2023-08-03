import { GetFoodDetailRes, GetFoodListReq, GetFoodListRes } from '@/types/food/remote';

import { GetFoodDetailReq } from '../types/food/remote';
import { client } from '.';

export const getFoodList = async ({ keyword }: GetFoodListReq) => {
  const { data } = await client.get<GetFoodListRes>('/pet-foods', {
    params: {
      keyword: Boolean(keyword.length) ? keyword.join(',') : undefined,
    },
  });

  return data;
};

export const getFood = () => {};

export const getFoodDetail = async ({ petFoodId }: GetFoodDetailReq) => {
  const { data } = await client.get<GetFoodDetailRes>(`/pet-foods/${petFoodId}`);

  return data;
};
