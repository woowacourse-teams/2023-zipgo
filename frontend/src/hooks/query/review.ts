import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import { getReviews, postReview } from '@/apis/review';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = { reviewList: 'reviewList' };

export const useReviewListQuery = (payload: Parameter<typeof getReviews>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewList],
    queryFn: () => getReviews(payload),
  });

  return {
    reviewList: data?.reviews,
    ...restQuery,
  };
};

export const useAddReviewMutation = () => {
  const queryClient = useQueryClient();

  const { mutate: addReview, ...addReviewRestMutation } = useMutation({
    mutationFn: postReview,
    onSuccess: () => {
      queryClient.invalidateQueries([QUERY_KEY.reviewList]);
    },
  });

  return { addReviewMutation: { addReview, ...addReviewRestMutation } };
};
