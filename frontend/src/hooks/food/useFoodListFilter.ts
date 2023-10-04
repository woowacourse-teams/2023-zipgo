import { useState } from 'react';

import { initialSelectedFilterList } from '@/context/food';
import type { KeywordEn } from '@/types/food/client';
import { parseCheckList } from '@/utils/parseCheckList';

export const useFoodListFilter = () => {
  const [selectedFilterList, setSelectedFilterList] = useState(initialSelectedFilterList);

  const parsedSelectedFilterList = parseCheckList(selectedFilterList);

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
