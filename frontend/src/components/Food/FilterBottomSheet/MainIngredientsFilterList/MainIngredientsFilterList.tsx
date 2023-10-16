import styled, { css } from 'styled-components';

import { MAIN_INGREDIENTS } from '@/constants/food';
import { useFoodFilterContext } from '@/context/food';
import { MainIngredientsFilter } from '@/types/food/client';

interface MainIngredientsFilterListProps {
  filterList: MainIngredientsFilter[];
}

const MainIngredientsFilterList = (props: MainIngredientsFilterListProps) => {
  const { filterList } = props;

  const { selectedFilterList, toggleFilter } = useFoodFilterContext();

  return (
    <MainIngredientsFilterListLayout>
      {filterList
        .concat(filterList)
        .concat(filterList)
        .concat(filterList)
        .concat(filterList)
        .concat(filterList)
        .map(({ id, ingredients }) => {
          const selected = selectedFilterList[MAIN_INGREDIENTS].has(ingredients);

          return (
            <IngredientFilterItem
              role="checkbox"
              key={id}
              aria-checked={selected}
              $selected={selected}
              onClick={() => toggleFilter(MAIN_INGREDIENTS, ingredients)}
            >
              {ingredients}
            </IngredientFilterItem>
          );
        })}
    </MainIngredientsFilterListLayout>
  );
};

export default MainIngredientsFilterList;

const MainIngredientsFilterListLayout = styled.ul`
  scrollbar-width: none;

  overflow-y: scroll;
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;

  max-height: 30rem;

  &::-webkit-scrollbar {
    width: 0;
    height: 0;
  }
`;

const IngredientFilterItem = styled.li<{
  $selected: boolean;
}>`
  cursor: pointer;

  display: flex;
  gap: 0.4rem;
  align-items: center;

  padding: 1rem 1.6rem;

  font-size: 1.4rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.primary};
  letter-spacing: 0.4px;
  list-style: none;

  background-color: transparent;
  border: 1px solid ${({ theme }) => theme.color.primary};
  border-radius: 20px;

  transition: background-color 0.2s, border 0.2s;

  ${({ $selected }) =>
    $selected &&
    css`
      background-color: #d0e6f9;
      border: 1px solid #d0e6f9;
    `};
`;
