import { useLocation } from 'react-router-dom';

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

  const { Funnel, setStep } = useFunnel(['별점선택', '리뷰작성'], '별점선택');

  return (
    <Funnel>
      <Funnel.Step name="별점선택">
        <ReviewStarRating
          reviewData={reviewData}
          onNext={() => {
            setStep(prev => '리뷰작성');
          }}
        />
      </Funnel.Step>
      <Funnel.Step name="리뷰작성">
        <ReviewAddition reviewData={reviewData} onPrev={() => setStep(prev => '별점선택')} />
      </Funnel.Step>
    </Funnel>
  );
};

export default ReviewFormFunnel;
