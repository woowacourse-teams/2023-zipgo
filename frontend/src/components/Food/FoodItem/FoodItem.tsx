import styled from 'styled-components';

import { Food } from '@/types/food/client';

interface FoodItemProps extends Food {}

const FoodItem = (foodItemProps: FoodItemProps) => {
  const { name, imageUrl, purchaseUrl } = foodItemProps;

  return (
    <FoodItemWrapper href={purchaseUrl}>
      <FoodImageWrapper>
        <FoodImage src={imageUrl} alt="Food image" />
      </FoodImageWrapper>
      <FoodName>{name}</FoodName>
    </FoodItemWrapper>
  );
};

export default FoodItem;

const FoodImage = styled.img`
  width: 100%;
  height: 100%;

  object-fit: cover;

  transition: all 200ms ease;
`;

const FoodItemWrapper = styled.a`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;

  width: 100%;

  text-decoration: none;

  &:hover {
    ${FoodImage} {
      scale: 1.05;
    }
  }
`;

const FoodImageWrapper = styled.div`
  overflow: hidden;

  width: 100%;
  height: 17rem;

  border: 1px solid ${({ theme }) => theme.color.grey200};
  border-radius: 20px;
`;

const FoodName = styled.p`
  overflow: hidden;
  display: -webkit-box;

  margin: 1.6rem 0;

  font-family: Pretendard, sans-serif;
  font-size: 1.4rem;
  font-weight: 600;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey700};
  text-overflow: ellipsis;
  letter-spacing: -0.5px;
  word-break: break-word;

  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
`;
