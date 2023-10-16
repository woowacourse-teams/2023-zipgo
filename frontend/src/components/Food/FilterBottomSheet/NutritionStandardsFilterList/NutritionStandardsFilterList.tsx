import styled from 'styled-components';

import EUFlag from '@/assets/svg/flag_eu.svg';
import USFlag from '@/assets/svg/flag_us.svg';
import { NUTRITION_STANDARD } from '@/constants/food';
import { useFoodFilterContext } from '@/context/food';
import { NutritionStandardsFilter, State } from '@/types/food/client';

interface NutritionStandardsFilterListProps {
  filterList: NutritionStandardsFilter[];
}
const NutritionStandardsFilterList = (props: NutritionStandardsFilterListProps) => {
  const { filterList } = props;

  const { selectedFilterList, toggleFilter } = useFoodFilterContext();

  return (
    <NutritionStandardsFilterListLayout>
      {filterList.map(({ id, nation }) => {
        const selected = selectedFilterList[NUTRITION_STANDARD].has(nation);

        const isUs = State.us === nation;

        return (
          <NutritionStandardsFilterItem
            key={id}
            role="checkbox"
            onClick={() => toggleFilter(NUTRITION_STANDARD, nation)}
            aria-checked={selected}
            $selected={selected}
          >
            <CountryFlag src={isUs ? USFlag : EUFlag} alt={isUs ? State.us : State.eu} />
            <span>{nation}</span>
            {selected && <span>✔</span>}
          </NutritionStandardsFilterItem>
        );
      })}
    </NutritionStandardsFilterListLayout>
  );
};

const NutritionStandardsFilterListLayout = styled.ul`
  & > li:not(:last-child) {
    margin-bottom: 1.2rem;
  }
`;

const NutritionStandardsFilterItem = styled.li<{
  $selected: boolean;
}>`
  cursor: pointer;

  display: flex;
  gap: 1.6rem;
  align-items: center;
  justify-content: flex-start;

  width: 100%;
  height: 7.3rem;
  padding: 1.7rem 2.4rem;

  list-style: none;

  background: ${({ theme, $selected }) => ($selected ? '#D0E6F9' : theme.color.grey200)};
  border-radius: 15px;

  & > span {
    font-size: 1.6rem;
    font-weight: 700;
    font-style: normal;
    line-height: 17px;
    color: ${({ theme }) => theme.color.primary};
    letter-spacing: -0.5px;
  }
`;

const CountryFlag = styled.img`
  width: 4rem;
  height: 4rem;
`;

export default NutritionStandardsFilterList;
