import { useInfiniteQuery, useQuery } from '@tanstack/react-query';

import { getFoodDetail, getFoodList, getFoodListFilterMeta } from '@/apis/food';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = {
  foodList: 'foodList',
  foodDetail: 'foodDetail',
  petFoods: 'petFoods',
  foodListFilterMeta: 'foodListFilterMeta',
};

const SIZE_PER_PAGE = 20;

export const useFoodListInfiniteQuery = (payload: Parameter<typeof getFoodList>) => {
  const { data, ...restQuery } = useInfiniteQuery({
    queryKey: [QUERY_KEY.petFoods],
    queryFn: ({ pageParam = { ...payload, size: String(SIZE_PER_PAGE) } }) =>
      getFoodList(pageParam),
    getNextPageParam: (lastFoodListRes, allFoodListRes) => {
      const lastFood = lastFoodListRes.petFoods.at(-1);
      const isLastPage =
        allFoodListRes.flatMap(foodListRes => foodListRes.petFoods).length >=
          lastFoodListRes.totalCount || lastFoodListRes.petFoods.length < SIZE_PER_PAGE;
      if (!lastFood || isLastPage) return undefined;

      return { ...payload, lastPetFoodId: String(lastFood.id), size: String(SIZE_PER_PAGE) };
    },
  });

  return {
    foodList: data?.pages.flatMap(page => page.petFoods),
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
    ...restQuery,
    keywords: data?.keywords,
    filterList: data?.filters,
  };
};
