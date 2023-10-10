import { styled } from 'styled-components';

import { useFilterSelectionDisplay } from '@/hooks/food/useFilterSelectionDisplay';
import { KeywordEn } from '@/types/food/client';

const FilterSelectionDisplay = () => {
  const { filterListQueryString, removeFilter } = useFilterSelectionDisplay();

  return (
    <SelectedFilterList>
      {Object.entries(filterListQueryString).map(([keyword, values]) =>
        values.split(',').map(value => (
          <SelectedFilterItem key={value} onClick={() => removeFilter(keyword as KeywordEn, value)}>
            {value}
            <FilterToggleButton type="button" aria-label={`${value}필터 선택 해제`}>
              x
            </FilterToggleButton>
          </SelectedFilterItem>
        )),
      )}
    </SelectedFilterList>
  );
};

export default FilterSelectionDisplay;

const SelectedFilterList = styled.ul`
  scrollbar-width: none;

  overflow-x: scroll;
  display: flex;
  gap: 0.4rem;
  align-items: center;

  width: calc(100% - 8.8rem - 1rem);
  margin-left: 1rem;

  &::-webkit-scrollbar {
    width: 0;
    height: 0;
  }
`;

const SelectedFilterItem = styled.li`
  cursor: pointer;

  overflow: hidden;
  flex-shrink: 0;

  height: 3.2rem;
  padding: 0.4rem;

  font-size: 1.2rem;
  font-weight: 500;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const FilterToggleButton = styled.button`
  cursor: pointer;

  display: inline-block;

  margin-left: 0.4rem;

  color: ${({ theme }) => theme.color.grey300};

  background: none;
  border: none;
`;
