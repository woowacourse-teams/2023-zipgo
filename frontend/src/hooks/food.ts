import { useState } from 'react';

import type { KeywordEn } from '@/types/food/client';

export const useFoodListFilter = (initialSelectedFilterList: Record<KeywordEn, Set<string>>) => {
  const [selectedFilterList, setSelectedFilterList] = useState(initialSelectedFilterList);

  const toggleFilter = (keyword: KeywordEn, filter: string) => {
    const targetFilterList = structuredClone(selectedFilterList)[keyword];
    const selected = targetFilterList.has(filter);

    selected ? targetFilterList.delete(filter) : targetFilterList.add(filter);

    setSelectedFilterList(prev => ({ ...prev, [keyword]: new Set(targetFilterList) }));
  };

  const resetSelectedFilterList = () => {
    setSelectedFilterList({
      nutritionStandards: new Set(),
      mainIngredients: new Set(),
      brands: new Set(),
      functionalities: new Set(),
    });
  };

  return { selectedFilterList, toggleFilter, resetSelectedFilterList };
};
