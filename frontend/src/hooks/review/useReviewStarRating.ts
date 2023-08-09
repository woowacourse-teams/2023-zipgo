import { useState } from 'react';

import { useValidParams } from '../@common/useValidParams';
import { useFoodDetailQuery } from '../query/food';

interface LocationState {
  state: {
    selectedRating?: number;
    isEditMode?: boolean;
    reviewId?: number;
  };
}

export const useReviewStarRating = (location: LocationState) => {
  const { selectedRating = 0, isEditMode = false, reviewId = -1 } = { ...location.state };
  const { petFoodId } = useValidParams(['petFoodId']);
  const { foodData } = useFoodDetailQuery({ petFoodId });
  const [rating, setRating] = useState(selectedRating);

  return {
    petFoodId,
    foodData,
    isEditMode,
    reviewId,
    rating,
    setRating,
  };
};
