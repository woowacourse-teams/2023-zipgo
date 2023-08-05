import styled, { css } from 'styled-components';

import { FUNCTIONALITIES } from '@/constants/food';
import { useFoodFilterContext } from '@/context/food';
import { FunctionalityFilter } from '@/types/food/client';

interface FunctionalityFilterListProps {
  filterList: FunctionalityFilter[];
}

const FunctionalityFilterList = (props: FunctionalityFilterListProps) => {
  const { filterList } = props;

  const { selectedFilterList, toggleFilter } = useFoodFilterContext();

  return (
    <FunctionalityFilterListLayout>
      {filterList.map(({ id, functionality }) => {
        const selected = selectedFilterList[FUNCTIONALITIES].has(functionality);

        return (
          <FunctionalityFilterItem
            role="checkbox"
            key={id}
            aria-checked={selected}
            $selected={selected}
            onClick={() => toggleFilter(FUNCTIONALITIES, functionality)}
          >
            {functionality}
          </FunctionalityFilterItem>
        );
      })}
    </FunctionalityFilterListLayout>
  );
};

export default FunctionalityFilterList;

const FunctionalityFilterListLayout = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
`;

const FunctionalityFilterItem = styled.li<{
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
