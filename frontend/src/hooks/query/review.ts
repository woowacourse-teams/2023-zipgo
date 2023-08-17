/* eslint-disable indent */
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import {
  deleteHelpfulReactions,
  deleteReview,
  getReview,
  getReviews,
  getReviewsMeta,
  getReviewSummary,
  postHelpfulReactions,
  postReview,
  putReview,
} from '@/apis/review';
import { Parameter } from '@/types/common/utility';
import { GetReviewsRes } from '@/types/review/remote';

const QUERY_KEY = {
  reviewItem: 'reviewItem',
  reviewList: 'reviewList',
  reviewSummary: 'reviewSummary',
  reviewListMeta: 'reviewListMeta',
};

export const useReviewItemQuery = (payload: Parameter<typeof getReview>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewItem, payload.reviewId],
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
    queryKey: [QUERY_KEY.reviewList, payload.petFoodId],
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

export const useReviewListAlignMeta = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewListMeta],
    queryFn: getReviewsMeta,
    select: ({ sortBy }) => sortBy,
  });

  return {
    metaData: data,
    ...restQuery,
  };
};

export const useReviewListFilterMeta = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.reviewListMeta],
    queryFn: getReviewsMeta,
    select: ({ sortBy, ...restMeta }) => restMeta,
  });

  return {
    metaData: data,
    ...restQuery,
  };
};

export const useToggleHelpfulReactionMutation = (reacted: boolean) => {
  const queryClient = useQueryClient();

  const { mutate: toggleHelpfulReaction, ...restMutation } = useMutation({
    mutationFn: reacted ? deleteHelpfulReactions : postHelpfulReactions,
    onMutate: async ({ petFoodId, reviewId }) => {
      await queryClient.cancelQueries({ queryKey: [QUERY_KEY.reviewList, petFoodId] });

      const previousReviewList = queryClient.getQueryData<GetReviewsRes>([
        QUERY_KEY.reviewList,
        petFoodId,
      ]);

      queryClient.setQueryData(
        [QUERY_KEY.reviewList, petFoodId],
        (previousReviewList?: GetReviewsRes) =>
          previousReviewList
            ? {
                ...previousReviewList,
                reviews: previousReviewList.reviews.map(review => {
                  if (review.id === reviewId) {
                    const cloned = review;

                    cloned.helpfulReaction.count += 1;
                    cloned.helpfulReaction.reacted = !cloned.helpfulReaction.reacted;

                    return cloned;
                  }

                  return review;
                }),
              }
            : previousReviewList,
      );

      return { previousReviewList };
    },
    onError: (err, { petFoodId }, context) => {
      queryClient.setQueryData([QUERY_KEY.reviewList, petFoodId], context?.previousReviewList);
    },
    onSettled: (_, __, { petFoodId }) => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEY.reviewList, petFoodId] });
    },
  });

  return {
    toggleHelpfulReaction,
    ...restMutation,
  };
};
