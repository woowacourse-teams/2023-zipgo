import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import {
  deleteReview,
  getReview,
  getReviews,
  getReviewsMeta,
  getReviewSummary,
  postReview,
  putReview,
} from '@/apis/review';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = {
  reviewItem: 'reviewItem',
  reviewList: 'reviewList',
  reviewSummary: 'reviewSummary',
  reviewListMeta: 'reviewListMeta',
};

export const useReviewItemQuery = (payload: Parameter<typeof getReview>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewItem],
    queryFn: () => getReview(payload),
    enabled: payload.reviewId > 0,
  });

  return {
    reviewItem: data,
    ...restQuery,
  };
};

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

export const useReviewListAlignMeta = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewItem],
    queryFn: getReviewsMeta,
    select: ({ sortBy, ...restMeta }) => sortBy,
  });

  return {
    metaData: data,
    ...restQuery,
  };
};

export const useReviewSummaryQuery = (payload: Parameter<typeof getReviewSummary>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewSummary],
    queryFn: () => getReviewSummary(payload),
  });

  return {
    summaryInfo: data,
    ...restQuery,
  };
};

export const useReviewListFilterMeta = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewItem],
    queryFn: getReviewsMeta,
    select: ({ sortBy, ...restMeta }) => restMeta,
  });

  return {
    metaData: data,
    ...restQuery,
  };
};
