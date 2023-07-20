import { styled } from 'styled-components';

import { Food } from '@/types/food/client';

import FoodItem from '../FoodItem/FoodItem';

interface FoodListProps {
  foodListData: Food[];
}

const FoodList = (foodListProps: FoodListProps) => {
  const { foodListData } = foodListProps;

  return (
    <FoodListWrapper>
      <FoodListComment>상품을 클릭시 구매 페이지로 이동합니다.</FoodListComment>
      <FoodListContainer>
        {foodListData.map(food => (
          <FoodItem key={food.id} {...food} />
        ))}
      </FoodListContainer>
    </FoodListWrapper>
  );
};

export default FoodList;

const FoodListWrapper = styled.div`
  padding: 2rem;
`;

const FoodListComment = styled.p`
  font-family: Pretendard, sans-serif;
  font-size: 1.2rem;
  font-weight: 400;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.5px;
`;

const FoodListContainer = styled.div`
  display: grid;
  grid-gap: 0.4rem 2rem;
  grid-template-columns: repeat(2, calc((100% - 2rem) / 2));
  place-items: center;
  justify-content: center;

  padding: 1.6rem 0;
`;
