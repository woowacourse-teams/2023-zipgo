import { styled } from 'styled-components';

import StarRatingDisplay from '@/components/@common/StarRating/StarRatingDisplay/StartRatingDisplay';
import type { FoodDetail } from '@/types/food/client';

type FoodProfileProps = FoodDetail;

const FoodProfile = (foodProfileProps: FoodProfileProps) => {
  const { name, imageUrl, rating, brand } = foodProfileProps;

  return (
    <FoodProfileWrapper>
      <FoodImg alt="식품 이미지" src={imageUrl} aria-label={`${name} 이미지`} />
      <BrandName aria-label={brand.name}>{brand.name}</BrandName>
      <FoodName aria-label={`식품 이름 ${name}`}>{name}</FoodName>
      <StarRatingDisplay rating={rating} size="large" displayRating />
    </FoodProfileWrapper>
  );
};

export default FoodProfile;

const FoodProfileWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
`;

const FoodImg = styled.img`
  width: 18rem;
  height: 18rem;

  object-fit: cover;
  background-color: ${({ theme }) => theme.color.grey200};
  border: 0.5px solid ${({ theme }) => theme.color.grey200};
  border-radius: 11%;
  box-shadow: 0 0.3799rem 1.5925rem 0 rgb(0 0 0 / 2%), 0 1.9rem 9.8rem 0 rgb(0 0 0 / 4%);
`;

const BrandName = styled.p`
  margin-top: 0.8rem;

  font-size: 1.4rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.5px;
`;

const FoodName = styled.h2`
  margin: 0.8rem 0;

  font-size: 2.2rem;
  font-weight: 700;
  line-height: 2.5rem;
  color: ${({ theme }) => theme.color.grey700};
  text-align: center;
  letter-spacing: -0.05rem;
  word-break: keep-all;
`;
