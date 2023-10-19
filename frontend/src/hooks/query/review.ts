/* eslint-disable no-param-reassign */
/* eslint-disable indent */
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

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
import { ONE_MINUTE } from '@/constants/time';
import { routerPath } from '@/router/routes';
import { Parameter } from '@/types/common/utility';
import { GetReviewsRes } from '@/types/review/remote';

import { FOOD_QUERY_KEY } from './food';

const QUERY_KEY = {
  reviewItem: 'reviewItem',
  reviewList: (petFoodId: string) => ['reviewList', petFoodId, location.search],
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
    queryKey: QUERY_KEY.reviewList(payload.petFoodId),
    queryFn: () => getReviews(payload),
    staleTime: ONE_MINUTE,
  });

  return {
    reviewList: data?.reviews,
    ...restQuery,
  };
};

export const useAddReviewMutation = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { mutate: addReview, ...addReviewRestMutation } = useMutation({
    mutationFn: postReview,
    onSuccess: (_, { petFoodId }) => {
      queryClient.invalidateQueries(QUERY_KEY.reviewList(petFoodId));
      queryClient.invalidateQueries([QUERY_KEY.reviewSummary]);
      queryClient.invalidateQueries(FOOD_QUERY_KEY.foodDetail(petFoodId));

      alert('리뷰 작성이 완료되었습니다.');

      navigate(routerPath.foodDetail({ petFoodId }));
    },
  });

  return { addReviewMutation: { addReview, ...addReviewRestMutation } };
};

export const useEditReviewMutation = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { mutate: editReview, ...editReviewRestMutation } = useMutation({
    mutationFn: putReview,
    onSuccess: (_, { petFoodId }) => {
      queryClient.invalidateQueries(QUERY_KEY.reviewList(petFoodId));
      queryClient.invalidateQueries([QUERY_KEY.reviewSummary]);
      queryClient.invalidateQueries(FOOD_QUERY_KEY.foodDetail(petFoodId));

      alert('리뷰 수정이 완료되었습니다.');

      navigate(routerPath.foodDetail({ petFoodId }));
    },
  });

  return { editReviewMutation: { editReview, ...editReviewRestMutation } };
};

export const useRemoveReviewMutation = () => {
  const queryClient = useQueryClient();

  const { mutate: removeReview, ...removeReviewRestMutation } = useMutation({
    mutationFn: deleteReview,
    onSuccess: (_, { petFoodId }) => {
      queryClient.invalidateQueries(QUERY_KEY.reviewList(petFoodId));
      queryClient.invalidateQueries([QUERY_KEY.reviewSummary]);
      queryClient.invalidateQueries(FOOD_QUERY_KEY.foodDetail(petFoodId));
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
      await queryClient.cancelQueries({
        queryKey: QUERY_KEY.reviewList(petFoodId),
      });

      const previousReviewList = structuredClone(
        queryClient.getQueryData<GetReviewsRes>(QUERY_KEY.reviewList(petFoodId)),
      );

      if (previousReviewList) {
        const updatedReviews = previousReviewList.reviews.map(review => {
          if (review.id === reviewId) {
            review.helpfulReaction.count += reacted ? -1 : 1;
            review.helpfulReaction.reacted = !review.helpfulReaction.reacted;
          }

          return review;
        });

        Object.assign(previousReviewList, { reviews: updatedReviews });
      }

      queryClient.setQueryData(QUERY_KEY.reviewList(petFoodId), previousReviewList);

      return { previousReviewList };
    },
    onError: (error, { petFoodId }, context) => {
      queryClient.setQueryData(QUERY_KEY.reviewList(petFoodId), context?.previousReviewList);
    },
    onSettled: (_, __, { petFoodId }) => {
      queryClient.invalidateQueries({
        queryKey: QUERY_KEY.reviewList(petFoodId),
      });
    },
  });

  return {
    toggleHelpfulReaction,
    ...restMutation,
  };
};
