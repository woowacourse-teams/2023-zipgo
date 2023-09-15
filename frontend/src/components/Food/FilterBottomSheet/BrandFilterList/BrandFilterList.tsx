import styled, { css } from 'styled-components';

import { BRANDS } from '@/constants/food';
import { useFoodFilterContext } from '@/context/food';
import { BrandFilter } from '@/types/food/client';

interface BrandFilterListProps {
  filterList: BrandFilter[];
}

const BrandFilterList = (props: BrandFilterListProps) => {
  const { filterList } = props;

  const { selectedFilterList, toggleFilter } = useFoodFilterContext();

  return (
    <BrandFilterListLayout>
      {filterList.map(({ id, brandName, brandUrl }) => {
        const selected = selectedFilterList[BRANDS].has(brandName);

        return (
          <BrandFilterListItem
            role="checkbox"
            key={id}
            aria-checked={selected}
            $selected={selected}
            onClick={() => toggleFilter(BRANDS, brandName)}
          >
            <BrandFilterLogo src={brandUrl} alt="브랜드 로고" />
            <BrandFilterName>{brandName}</BrandFilterName>
          </BrandFilterListItem>
        );
      })}
    </BrandFilterListLayout>
  );
};

export default BrandFilterList;

const BrandFilterListLayout = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
  justify-content: space-between;
`;

const BrandFilterListItem = styled.li<{
  $selected: boolean;
}>`
  cursor: pointer;

  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: center;

  width: 10.6rem;
  height: 13.8rem;
  padding: 0.9rem 0.8rem;

  border-radius: 20px;

  transition: background-color 0.2s, border 0.2s;

  ${({ $selected }) =>
    $selected &&
    css`
      background-color: #d0e6f9;
      border: 1px solid #d0e6f9;
    `};
`;

const BrandFilterLogo = styled.img`
  width: 9rem;
  height: 9rem;

  border-radius: 20px;
`;

const BrandFilterName = styled.span`
  font-size: 1.6rem;
  font-weight: 500;
  font-style: normal;
  line-height: 17px;
  color: ${({ theme }) => theme.color.grey600};
  text-align: center;
  letter-spacing: -0.5px;
`;
