import { useQuery } from '@tanstack/react-query';

import { getFoodDetail, getFoodList } from '@/apis/food';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = { foodList: 'foodList', foodDetail: 'foodDetail' };

const useFoodListQuery = (payload: Parameter<typeof getFoodList>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.foodList],
    queryFn: () => getFoodList(payload),
  });

  return {
    foodList: data?.petFoods,
    ...restQuery,
  };
};

const useFoodDetailQuery = (payload: Parameter<typeof getFoodDetail>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [`${QUERY_KEY.foodDetail}${payload.petFoodId}`],
    queryFn: () => getFoodDetail(payload),
  });

  return {
    foodData: data,
    ...restQuery,
  };
};

export { useFoodDetailQuery, useFoodListQuery };
