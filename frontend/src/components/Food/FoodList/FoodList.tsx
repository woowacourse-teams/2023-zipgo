import { styled } from 'styled-components';

import { useInfiniteFoodListScroll } from '@/hooks/food/useInfiniteFoodListScroll';

import FoodItem from '../FoodItem/FoodItem';

const FoodList = () => {
  const { foodList, hasNextPage, targetRef } = useInfiniteFoodListScroll();

  if (!foodList) return null;

  const hasResult = Boolean(foodList.length);

  return (
    <FoodListWrapper>
      {hasResult ? (
        <FoodListContainer>
          {foodList.map(food => (
            <FoodItem key={food.id} {...food} />
          ))}
        </FoodListContainer>
      ) : (
        <NoResultContainer>
          <NoResultText>
            필터링 결과가 없어요. <br />
            다른 옵션을 선택해주세요.
          </NoResultText>
        </NoResultContainer>
      )}
      <ObserverTarget
        ref={targetRef}
        aria-label={hasNextPage ? '' : '모든 식품 목록을 불러왔습니다'}
      />
    </FoodListWrapper>
  );
};

export default FoodList;

const FoodListWrapper = styled.div`
  padding: 2.6rem 0;
`;

const FoodListContainer = styled.div`
  display: grid;
  grid-gap: 2rem;
  grid-template-columns: repeat(2, calc((100% - 2rem) / 2));
  place-items: center;
  justify-content: center;
`;

const NoResultContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 13rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border-radius: 20px;
`;

const NoResultText = styled.h3`
  font-family: Pretendard, sans-serif;
  font-size: 1.2rem;
  font-weight: 600;
  line-height: 1.6rem;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;
`;

const ObserverTarget = styled.p`
  font-size: 1.4rem;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;
`;
