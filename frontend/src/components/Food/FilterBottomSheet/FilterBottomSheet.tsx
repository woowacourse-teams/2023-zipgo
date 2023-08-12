import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';

import SettingsIcon from '@/assets/svg/settings_outline_icon.svg';
import { Dialog } from '@/components/@common/Dialog/Dialog';
import QueryBoundary from '@/components/@common/QueryBoundary';
import Tabs from '@/components/@common/Tabs/Tabs';
import { FoodFilterProvider, useFoodFilterContext } from '@/context/food';
import { useFoodListFilterMetaQuery } from '@/hooks/query/food';
import { generateQueryString, routerPath } from '@/router/routes';
import type { KeywordEn } from '@/types/food/client';
import { translateKeyword } from '@/utils/food';
import { getComputedStyleOfSC } from '@/utils/styled-components';

import BrandFilterList from './BrandFilterList/BrandFilterList';
import FunctionalityFilterList from './FunctionalityFilterList/FunctionalityFilterList';
import MainIngredientsFilterList from './MainIngredientsFilterList/MainIngredientsFilterList';
import NutritionStandardsFilterList from './NutritionStandardsFilterList/NutritionStandardsFilterList';

const FilterBottomSheet = () => (
  <QueryBoundary>
    <FoodFilterProvider>
      <Dialog>
        <Dialog.Trigger asChild>
          <DialogTrigger type="button">
            <img src={SettingsIcon} alt="필터 버튼 아이콘" />
            <span>필터</span>
          </DialogTrigger>
        </Dialog.Trigger>
        <Dialog.Portal>
          <Dialog.BackDrop />
          <Dialog.Content asChild>
            {({ openHandler }) => <KeywordContent toggleDialog={openHandler} />}
          </Dialog.Content>
        </Dialog.Portal>
      </Dialog>
    </FoodFilterProvider>
  </QueryBoundary>
);

interface KeywordContentProps {
  toggleDialog: VoidFunction;
}

const KeywordContent = (props: KeywordContentProps) => {
  const { toggleDialog } = props;

  const { keywords, filterList } = useFoodListFilterMetaQuery();

  const { parsedSelectedFilterList, resetSelectedFilterList } = useFoodFilterContext();

  const navigate = useNavigate();

  if (!keywords || !filterList) return null;

  const confirm = () => {
    toggleDialog();
    navigate(`${routerPath.home()}${generateQueryString(parsedSelectedFilterList)}`);
  };

  const getFilterListByKeyword = (keyword: KeywordEn) => {
    switch (keyword) {
      case 'nutritionStandards':
        return <NutritionStandardsFilterList filterList={filterList[keyword]} />;

      case 'mainIngredients':
        return <MainIngredientsFilterList filterList={filterList[keyword]} />;

      case 'brands':
        return <BrandFilterList filterList={filterList[keyword]} />;

      case 'functionalities':
        return <FunctionalityFilterList filterList={filterList[keyword]} />;

      default:
    }

    return null;
  };

  return (
    <Layout>
      <Tabs defaultValue={keywords[0]} asChild>
        <NavWrapper>
          <Tabs.List aria-label="식품 필터 카테고리 탭 목록" asChild>
            <NavList>
              {keywords.map(keyword => (
                <Tabs.Trigger key={keyword} value={keyword} asChild>
                  {({ selected }) => (
                    <NavItem $selected={selected}>{translateKeyword({ keyword })}</NavItem>
                  )}
                </Tabs.Trigger>
              ))}
            </NavList>
          </Tabs.List>
          {keywords.map(keyword => (
            <Tabs.Content key={keyword} value={keyword} asChild>
              <NavContent $paddingBottom={getComputedStyleOfSC(ButtonContainer, 'height')}>
                <FilterContainer>
                  <Keyword>{translateKeyword({ keyword })}</Keyword>
                  {getFilterListByKeyword(keyword)}
                </FilterContainer>
                <ButtonContainer>
                  <ResetButton onClick={resetSelectedFilterList}>초기화</ResetButton>
                  <ConfirmButton type="button" onClick={confirm}>
                    검색 필터 적용
                  </ConfirmButton>
                </ButtonContainer>
              </NavContent>
            </Tabs.Content>
          ))}
        </NavWrapper>
      </Tabs>
    </Layout>
  );
};

export default FilterBottomSheet;

const Layout = styled.div`
  position: fixed;
  z-index: 1001;
  bottom: 0;

  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  align-items: flex-start;

  width: 100vw;
  height: 57.4rem;

  background: ${({ theme }) => theme.color.grey200};
  border-radius: 20px 20px 0 0;

  animation: ${({ theme }) => theme.animation.bottomSheetAppear} 0.5s cubic-bezier(0.2, 0.6, 0.3, 1);
`;

const DialogTrigger = styled.button`
  display: flex;
  gap: 0.4rem;
  align-items: center;

  padding: 1rem 1.6rem;

  background-color: transparent;
  border: 1px solid ${({ theme }) => theme.color.primary};
  border-radius: 20px;

  & > span {
    font-size: 1.4rem;
    font-weight: 500;
    font-style: normal;
    color: ${({ theme }) => theme.color.primary};
    letter-spacing: 0.4px;
  }
`;

const NavWrapper = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  background-color: ${({ theme }) => theme.color.white};
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
`;

const NavList = styled.nav`
  display: flex;
  gap: 2rem;
  align-items: center;

  width: 100%;
  height: 6rem;
  padding: 0 2rem;

  list-style: none;

  border-bottom: 1px solid ${({ theme }) => theme.color.grey250};
`;

const NavItem = styled.button<{
  $selected: boolean;
}>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  height: 100%;
  padding: 0;

  font-size: 1.8rem;
  line-height: 1.8rem;
  letter-spacing: -0.5px;

  background-color: transparent;
  border: none;
  outline: none;

  transition: all 200ms ease-in-out;

  ${({ theme, $selected }) =>
    $selected
      ? css`
          color: ${theme.color.black};

          border-bottom: 2px solid ${({ theme }) => theme.color.black};
        `
      : css`
          color: ${theme.color.grey400};
        `}
`;

const NavContent = styled.div<{
  $paddingBottom?: string;
}>`
  position: relative;

  flex-grow: 1;

  width: 100%;
  padding-bottom: ${({ $paddingBottom }) => $paddingBottom && $paddingBottom};

  background: ${({ theme }) => theme.color.grey200};
`;

const FilterContainer = styled.div`
  margin-top: 1.5rem;
  padding: 2rem;

  background-color: ${({ theme }) => theme.color.white};
`;

const ButtonContainer = styled.div`
  position: absolute;
  bottom: 0;

  display: flex;
  align-items: center;
  justify-content: space-evenly;

  width: 100%;
  height: 10rem;

  background-color: ${({ theme }) => theme.color.white};
`;

const ConfirmButton = styled.button`
  width: 27.8rem;
  height: 5.1rem;

  font-size: 1.6rem;
  font-weight: 700;
  font-style: normal;
  line-height: 24px;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: 0.2px;

  background-color: ${({ theme }) => theme.color.primary};
  border: none;
  border-radius: 16px;
`;

const ResetButton = styled.button`
  width: 7rem;
  height: 5.1rem;

  font-size: 1.6rem;
  font-weight: 500;
  font-style: normal;
  line-height: 24px;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: 0.2px;

  background-color: transparent;
  border: none;
`;

const Keyword = styled.div`
  margin-bottom: 2rem;

  font-size: 2rem;
  font-weight: 700;
  font-style: normal;
  line-height: 17px;
  color: ${({ theme }) => theme.color.grey800};
  text-align: start;
  letter-spacing: -0.5px;
`;
