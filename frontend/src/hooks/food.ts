import { useState } from 'react';

import { initialSelectedFilterList, SelectedFilterList } from '@/context/food';
import type { KeywordEn } from '@/types/food/client';

export const useFoodListFilter = () => {
  const [selectedFilterList, setSelectedFilterList] = useState(initialSelectedFilterList);

  const parsedSelectedFilterList = Object.entries(selectedFilterList).reduce(
    (acc, [keyword, filterList]) => ({ ...acc, [keyword]: Array.from(filterList) }),
    {},
  ) as Partial<Record<keyof SelectedFilterList, string[]>>;

  const toggleFilter = (keyword: KeywordEn, filter: string) => {
    const targetFilterList = structuredClone(selectedFilterList)[keyword];
    const selected = targetFilterList.has(filter);

    selected ? targetFilterList.delete(filter) : targetFilterList.add(filter);

    setSelectedFilterList(prev => ({ ...prev, [keyword]: new Set(targetFilterList) }));
  };

  const resetSelectedFilterList = () => {
    setSelectedFilterList(initialSelectedFilterList);
  };

  return { selectedFilterList, parsedSelectedFilterList, toggleFilter, resetSelectedFilterList };
};
