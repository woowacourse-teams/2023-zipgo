import { GetFoodListReq, GetFoodListRes } from '@/types/food/remote';

import { client } from '.';

export const getFoodList = async ({ keyword }: GetFoodListReq) => {
  const { data } = await client.get<GetFoodListRes>('/pet-foods', {
    params: {
      keyword: keyword.length ? keyword.join(',') : undefined,
    },
  });

  return data;
};

export const getFood = () => {};
