import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import PageHeader from '@/components/@common/PageHeader/PageHeader';
import Template from '@/components/@common/Template';
import FoodInfoInReviewForm from '@/components/Review/ReviewForm/FoodInfoInReviewForm/FoodInfoInReviewForm';
import ReviewForm from '@/components/Review/ReviewForm/ReviewForm';
import { useValidParams } from '@/hooks/@common/useValidParams';

interface LocationState {
  state: {
    selectedRating: number;
    isEditMode: boolean;
    reviewId: number;
  };
}

const ReviewAddition = () => {
  const navigate = useNavigate();
  const { petFoodId } = useValidParams(['petFoodId']);
  const location = useLocation() as LocationState;
  const { selectedRating, isEditMode, reviewId } = { ...location.state };

  const goBack = () => navigate(-1);

  return (
    <Template.WithoutHeader footer={false}>
      <PageHeader onClick={goBack} />
      <Container>
        <FoodInfoWrapper>
          <FoodInfoInReviewForm petFoodId={Number(petFoodId)} rating={selectedRating} />
        </FoodInfoWrapper>
        <ReviewForm
          petFoodId={Number(petFoodId)}
          rating={selectedRating}
          isEditMode={isEditMode}
          reviewId={reviewId}
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
