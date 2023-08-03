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
  padding: 2.6rem 0;
`;

const FoodListContainer = styled.div`
  display: grid;
  grid-gap: 0.4rem 2rem;
  grid-template-columns: repeat(2, calc((100% - 2rem) / 2));
  place-items: center;
  justify-content: center;
`;
