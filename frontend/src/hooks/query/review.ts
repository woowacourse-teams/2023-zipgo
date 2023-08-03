import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

import { deleteReview, getReviews, postReview, putReview } from '@/apis/review';
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

  const { mutateAsync: addReview, ...addReviewRestMutation } = useMutation({
    mutationFn: postReview,
    onSuccess: () => {
      queryClient.invalidateQueries([QUERY_KEY.reviewList]);
    },
  });

  return { addReviewMutation: { addReview, ...addReviewRestMutation } };
};

export const useEditReviewMutation = () => {
  const queryClient = useQueryClient();

  const { mutateAsync: editReview, ...editReviewRestMutation } = useMutation({
    mutationFn: putReview,
    onSuccess: () => {
      queryClient.invalidateQueries([QUERY_KEY.reviewList]);
    },
  });

  return { editReviewMutation: { editReview, ...editReviewRestMutation } };
};

export const useRemoveReviewMutation = () => {
  const queryClient = useQueryClient();

  const { mutate: removeReview, ...removeReviewRestMutation } = useMutation({
    mutationFn: deleteReview,
    onSuccess: () => {
      queryClient.invalidateQueries([QUERY_KEY.reviewList]);
    },
  });

  return { removeReviewMutation: { removeReview, ...removeReviewRestMutation } };
};
