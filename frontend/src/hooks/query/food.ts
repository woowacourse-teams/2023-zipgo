import { useQuery } from '@tanstack/react-query';

import { getFoodDetail, getFoodList, getFoodListFilterMeta } from '@/apis/food';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = {
  foodList: 'foodList',
  foodDetail: 'foodDetail',
  petFoods: 'petFoods',
  foodListFilterMeta: 'foodListFilterMeta',
};

export const useFoodListQuery = (payload: Parameter<typeof getFoodList>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.petFoods],
    queryFn: () => getFoodList(payload),
  });

  return {
    foodList: data?.petFoods,
    ...restQuery,
  };
};

export const useFoodDetailQuery = (payload: Parameter<typeof getFoodDetail>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [`${QUERY_KEY.foodDetail}${payload.petFoodId}`],
    queryFn: () => getFoodDetail(payload),
  });

  return {
    foodData: data,
    ...restQuery,
  };
};

export const useFoodListFilterMetaQuery = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.foodListFilterMeta],
    queryFn: getFoodListFilterMeta,
  });

  return {
    ...data,
    ...restQuery,
  };
};
