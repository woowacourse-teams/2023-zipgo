import { styled } from 'styled-components';

import StarRatingDisplay from '@/components/@common/StarRating/StarRatingDisplay/StartRatingDisplay';
import { SATISFACTION_MESSAGES } from '@/constants/review';
import { useFoodDetailQuery } from '@/hooks/query/food';

interface FoodInfoInReviewFormProps {
  petFoodId: number;
  rating: number;
}

const FoodInfoInReviewForm = (foodInfoInReviewFormProps: FoodInfoInReviewFormProps) => {
  const { petFoodId, rating } = foodInfoInReviewFormProps;
  const { foodData } = useFoodDetailQuery({ petFoodId: String(petFoodId) });

  if (!foodData) {
    throw new Error('죄송합니다, 해당 식품 정보를 찾을 수 없습니다.');
  }

  return (
    <FoodInfoInReviewContainer>
      <FoodImageWrapper>
        <FoodImage src={foodData.imageUrl} alt={`${foodData.name}`} />
      </FoodImageWrapper>
      <div>
        <FoodName>{foodData.name}</FoodName>
        <StarRatingDisplay rating={rating} />
        <Caption>{SATISFACTION_MESSAGES[rating]}</Caption>
      </div>
    </FoodInfoInReviewContainer>
  );
};

export default FoodInfoInReviewForm;

const FoodInfoInReviewContainer = styled.div`
  display: flex;
  align-items: center;
`;

const FoodImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 10.4rem;
  height: 10.4rem;
  margin-right: 2rem;

  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.grey200};
  border-radius: 20px;
  box-shadow: 0 4px 16px rgb(0 0 0 / 2%);
`;

const FoodImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
`;

const FoodName = styled.p`
  overflow: hidden;
  display: -webkit-box;

  margin-bottom: 1.2rem;

  font-size: 1.8rem;
  font-weight: 700;
  line-height: 1.9rem;
  color: ${({ theme }) => theme.color.black};
  text-overflow: ellipsis;
  letter-spacing: -0.5px;
  word-break: break-word;

  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
`;

const Caption = styled.p`
  margin-top: 1.2rem;

  font-size: 1.5rem;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey500};
  letter-spacing: -0.5px;
`;
