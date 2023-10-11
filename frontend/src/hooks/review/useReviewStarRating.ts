import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import { routerPath } from '@/router/routes';

import { useValidParams } from '../@common/useValidParams';
import { useFoodDetailQuery } from '../query/food';

interface LocationState {
  state: {
    selectedRating?: number;
    isEditMode?: boolean;
    reviewId?: number;
  };
}

// export const useReviewStarRating = (location: LocationState) => {
export const useReviewStarRating = () => {
  const navigate = useNavigate();
  const location: LocationState = useLocation();
  const { selectedRating = 0, isEditMode = false, reviewId = -1 } = { ...location.state };
  const { petFoodId } = useValidParams(['petFoodId']);
  const { foodData } = useFoodDetailQuery({ petFoodId });
  const [rating, setRating] = useState(selectedRating);

  const onClickStar = (selectedRating: number) => {
    navigate(routerPath.reviewAddition({ petFoodId }), {
      state: { selectedRating, isEditMode, reviewId },
    });
  };

  const goBack = () => navigate(routerPath.back);

  return {
    petFoodId,
    foodData,
    isEditMode,
    reviewId,
    rating,
    setRating,
    onClickStar,
    goBack,
  };
};
