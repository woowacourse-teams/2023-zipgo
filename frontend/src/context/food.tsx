/* eslint-disable indent */
/* eslint-disable no-spaced-func */
import { createContext, useContext, useMemo } from 'react';

import { useFoodListFilter } from '@/hooks/food';
import type { KeywordEn } from '@/types/food/client';
import { getValidProps, PropsWithRenderProps } from '@/utils/compound';

export type SelectedFilterList = Record<KeywordEn, Set<string>>;

export const initialSelectedFilterList: SelectedFilterList = {
  nutritionStandards: new Set(),
  mainIngredients: new Set(),
  brands: new Set(),
  functionalities: new Set(),
};

const FoodFilterContext = createContext<{
  selectedFilterList: SelectedFilterList;
  parsedSelectedFilterList: Partial<Record<keyof SelectedFilterList, string[]>>;
  toggleFilter(keyword: KeywordEn, filter: string): void;
  resetSelectedFilterList: VoidFunction;
}>({
  selectedFilterList: initialSelectedFilterList,
  parsedSelectedFilterList: {},
  toggleFilter: () => {},
  resetSelectedFilterList: () => {},
});

export const useFoodFilterContext = () => useContext(FoodFilterContext);

export const FoodFilterProvider = (
  props: PropsWithRenderProps<unknown, { selectedFilterList: SelectedFilterList }>,
) => {
  const { resolveChildren } = getValidProps(props);

  const { selectedFilterList, parsedSelectedFilterList, toggleFilter, resetSelectedFilterList } =
    useFoodListFilter();

  const { children } = resolveChildren({ selectedFilterList });

  const filterContextValue = useMemo(
    () => ({
      selectedFilterList,
      parsedSelectedFilterList,
      toggleFilter,
      resetSelectedFilterList,
    }),
    [selectedFilterList],
  );

  return (
    <FoodFilterContext.Provider value={filterContextValue}>{children}</FoodFilterContext.Provider>
  );
};
