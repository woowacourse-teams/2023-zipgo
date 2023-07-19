import { GetFoodListReq, GetFoodListRes } from '@/types/food/remote';

import { client } from '.';

export const getFoodList = async ({ keyword }: GetFoodListReq) => {
  const { data } = await client.get<GetFoodListRes>(
    `/api/v1/foodList?keyword=${keyword.join(',')}`,
  );

  return data;
};

export const getFood = () => {};
