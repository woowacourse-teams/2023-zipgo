import { Link } from 'react-router-dom';
import styled from 'styled-components';

import { Food, FoodDetail } from '@/types/food/client';

interface FoodItemProps extends Food {}

const FoodItem = (foodItemProps: FoodItemProps) => {
  const { foodName, brandName, imageUrl, id } = foodItemProps;

  return (
    <FoodItemWrapper to={`pet-food/${id}`}>
      <FoodImageWrapper>
        <FoodImage src={imageUrl} alt="Food image" />
      </FoodImageWrapper>
      <BrandName>{brandName}</BrandName>
      <FoodName>{foodName}</FoodName>
    </FoodItemWrapper>
  );
};

export default FoodItem;

const FoodImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;

  transition: all 200ms ease;
`;

const FoodItemWrapper = styled(Link)`
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
  position: relative;

  overflow: hidden;

  width: 100%;
  height: 0;
  padding-top: 100%;

  border: 1px solid ${({ theme }) => theme.color.grey200};
  border-radius: 14%;
`;

const BrandName = styled.span`
  margin-top: 1.6rem;

  font-size: 1.4rem;
  font-weight: 600;
  font-style: normal;
  line-height: 17px;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.5px;
`;

const FoodName = styled.p`
  overflow: hidden;
  display: -webkit-box;

  margin: 1rem 0;

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
