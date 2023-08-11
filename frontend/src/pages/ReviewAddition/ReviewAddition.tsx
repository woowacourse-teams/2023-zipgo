import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import PageHeader from '@/components/@common/PageHeader/PageHeader';
import Template from '@/components/@common/Template';
import FoodInfoInReviewForm from '@/components/Review/ReviewForm/FoodInfoInReviewForm/FoodInfoInReviewForm';
import ReviewForm from '@/components/Review/ReviewForm/ReviewForm';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { routerPath } from '@/router/routes';
import { AdverseReaction, StoolCondition, TastePreference } from '@/types/review/client';

interface LocationState {
  state: {
    petFoodId: number;
    name: string;
    imageUrl: string;
    reviewDetail: {
      reviewId: number;
      tastePreference: TastePreference;
      stoolCondition: StoolCondition;
      adverseReactions: AdverseReaction[];
      comment: string;
    };
  };
}

const ReviewAddition = () => {
  const navigate = useNavigate();
  const { rating, isEditMode } = useValidParams(['rating', 'isEditMode']);
  const location = useLocation() as LocationState;
  const { petFoodId, name, imageUrl, reviewDetail } = { ...location.state };

  const goBack = () => navigate(routerPath.back);

  return (
    <Template.WithoutHeader footer={false}>
      <PageHeader onClick={goBack} />
      <Container>
        <FoodInfoWrapper>
          <FoodInfoInReviewForm name={name} rating={Number(rating)} imageUrl={imageUrl} />
        </FoodInfoWrapper>
        <ReviewForm
          petFoodId={petFoodId}
          rating={Number(rating)}
          isEditMode={isEditMode === 'true'}
          reviewDetail={reviewDetail}
        />
      </Container>
    </Template.WithoutHeader>
  );
};

export default ReviewAddition;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100vw;
  height: 100vh;
  padding: 2rem;
`;

const FoodInfoWrapper = styled.div`
  width: 100%;
  margin-top: 24rem;
  margin-bottom: 2.4rem;
  padding: 2.4rem 0;

  border-bottom: 1px solid ${({ theme }) => theme.color.grey200};
`;
