import { useQuery } from '@tanstack/react-query';

import { getFoodList } from '@/apis/food';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = { foodList: 'foodList' };

const useFoodListQuery = (payload: Parameter<typeof getFoodList>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.foodList],
    queryFn: () => getFoodList(payload),
  });

  return {
    foodList: data?.foodList,
    ...restQuery,
  };
};

export default useFoodListQuery;
