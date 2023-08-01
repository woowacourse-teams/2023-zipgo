import { styled } from 'styled-components';

import StarRatingDisplay from '@/components/@common/StarRating/StarRatingDisplay/StartRatingDisplay';
import { SATISFACTION_MESSAGES } from '@/constants/review';

interface FoodInfoInReviewFormProps {
  name: string;
  rating: number;
  imageUrl: string;
}

const FoodInfoInReviewForm = (foodInfoInReviewFormProps: FoodInfoInReviewFormProps) => {
  const { name, rating, imageUrl } = foodInfoInReviewFormProps;

  return (
    <FoodInfoInReviewContainer>
      <FoodImageWrapper>
        <FoodImage src={imageUrl} alt={`${name}`} />
      </FoodImageWrapper>
      <div>
        <FoodName>{name}</FoodName>
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
