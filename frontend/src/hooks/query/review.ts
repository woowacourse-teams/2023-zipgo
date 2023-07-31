import { useQuery } from '@tanstack/react-query';

import { getReviews } from '@/apis/review';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = { reviewList: 'reviewList' };

const useReviewListQuery = (payload: Parameter<typeof getReviews>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewList],
    queryFn: () => getReviews(payload),
  });

  return {
    reviewList: data?.reviews,
    ...restQuery,
  };
};

export default useReviewListQuery;
