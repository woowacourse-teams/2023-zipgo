import { styled } from 'styled-components';

import PageHeader from '@/components/@common/PageHeader/PageHeader';
import Template from '@/components/@common/Template';
import FoodInfoInReviewForm from '@/components/Review/ReviewForm/FoodInfoInReviewForm/FoodInfoInReviewForm';
import ReviewForm from '@/components/Review/ReviewForm/ReviewForm';

import { ReviewData } from './ReviewFormFunnel';

interface ReviewAdditionProps {
  reviewData: ReviewData;
  onPrev: VoidFunction;
}

const ReviewAddition = (props: ReviewAdditionProps) => {
  const { reviewData, onPrev } = props;
  const { review } = reviewData;

  return (
    <Template.WithoutHeader footer={false}>
      <PageHeader onClick={onPrev} />
      <Container>
        <FoodInfoWrapper>
          <FoodInfoInReviewForm petFoodId={review.petFoodId} rating={review.rating} />
        </FoodInfoWrapper>
        <ReviewForm reviewData={reviewData} />
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
