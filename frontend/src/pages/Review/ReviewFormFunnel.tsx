import { useLocation } from 'react-router-dom';

import { REVIEW_FORM_STEP } from '@/constants/review';
import { useFunnel } from '@/hooks/@common/useFunnel';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { ReviewSetAction, useReviewForm } from '@/hooks/review/useReviewForm';
import { PostReviewReq } from '@/types/review/remote';

import ReviewAddition from './ReviewAddition';
import ReviewStarRating from './ReviewStarRating';

interface LocationState {
  state: {
    selectedRating?: number;
    isEditMode?: boolean;
    reviewId?: number;
  };
}

export interface ReviewData {
  review: PostReviewReq;
  reviewDispatch: React.Dispatch<ReviewSetAction>;
  onSubmitReview: (e: React.FormEvent<HTMLFormElement>) => void;
  isValidComment: boolean;
}

const ReviewFormFunnel = () => {
  const location: LocationState = useLocation();
  const { selectedRating = 0, isEditMode = false, reviewId = -1 } = { ...location.state };
  const { petFoodId } = useValidParams(['petFoodId']);
  const reviewData = useReviewForm({
    petFoodId: Number(petFoodId),
    rating: selectedRating,
    isEditMode,
    reviewId,
  });

  const { Funnel, setStep } = useFunnel(REVIEW_FORM_STEP, REVIEW_FORM_STEP[0]);

  return (
    <Funnel>
      <Funnel.Step name="STAR_RATING">
        <ReviewStarRating
          reviewData={reviewData}
          onNext={() => {
            setStep(prev => 'REVIEW_SUBMISSION');
          }}
        />
      </Funnel.Step>
      <Funnel.Step name="REVIEW_SUBMISSION">
        <ReviewAddition reviewData={reviewData} onPrev={() => setStep(prev => 'STAR_RATING')} />
      </Funnel.Step>
    </Funnel>
  );
};

export default ReviewFormFunnel;
