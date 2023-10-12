import { ReviewStarRatingProps } from '@/pages/Review/ReviewStarRating';

import useEasyNavigate from '../@common/useEasyNavigate';
import { useFoodDetailQuery } from '../query/food';
import { REVIEW_ACTION_TYPES } from './useReviewForm';

export const useReviewStarRating = (props: ReviewStarRatingProps) => {
  const {
    reviewData: {
      review: { petFoodId },
      reviewDispatch,
    },
  } = props;
  const { goBackSafely } = useEasyNavigate();
  const { foodData } = useFoodDetailQuery({ petFoodId: petFoodId.toString() });

  const updateRating = (selectedRating: number) => {
    reviewDispatch({
      type: REVIEW_ACTION_TYPES.SET_RATING,
      rating: selectedRating,
    });
  };

  return {
    foodData,
    goBackSafely,
    updateRating,
  };
};
