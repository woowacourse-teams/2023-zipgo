import { FORM_EXIT_CONFIRMATION_MESSAGE } from '@/constants/common';
import { ReviewStarRatingProps } from '@/pages/ReviewStarRating/ReviewStarRating';

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
  const { goBack } = useEasyNavigate();
  const { foodData } = useFoodDetailQuery({ petFoodId: petFoodId.toString() });

  const goBackSafely = () => {
    confirm(FORM_EXIT_CONFIRMATION_MESSAGE) && goBack();
  };

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
