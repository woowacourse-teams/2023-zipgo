import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import BackIcon from '@/assets/svg/back_btn.svg';
import Template from '@/components/@common/Template';
import FoodInfoInReviewForm from '@/components/Review/ReviewForm/FoodInfoInReviewForm/FoodInfoInReviewForm';
import ReviewForm from '@/components/Review/ReviewForm/ReviewForm';

interface LocationState {
  state: {
    petFoodId: number;
    name: string;
    rating: number;
    imageUrl: string;
  };
}

const ReviewAddition = () => {
  const navigate = useNavigate();
  const location = useLocation() as LocationState;
  const { petFoodId, name, rating, imageUrl } = { ...location.state };

  const goBack = () => navigate(-1);

  return (
    <Template.WithoutHeader footer={false}>
      <Container>
        <BackButton type="button" aria-label="뒤로가기" onClick={goBack}>
          <img src={BackIcon} alt="" />
        </BackButton>
        <FoodInfoWrapper>
          <FoodInfoInReviewForm name={name} rating={rating} imageUrl={imageUrl} />
        </FoodInfoWrapper>
        <ReviewForm petFoodId={petFoodId} rating={rating} />
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

const BackButton = styled.button`
  all: unset;

  cursor: pointer;

  position: fixed;
  top: 6.8rem;
  left: 2rem;
`;

const FoodInfoWrapper = styled.div`
  width: 100%;
  margin-top: 17.4rem;
  margin-bottom: 2.4rem;
  padding: 2.4rem 0;

  border-bottom: 1px solid ${({ theme }) => theme.color.grey200};
`;
