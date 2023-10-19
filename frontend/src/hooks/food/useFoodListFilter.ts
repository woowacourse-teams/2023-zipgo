import { useEffect, useState } from 'react';

import { initialSelectedFilterList } from '@/context/food';
import type { KeywordEn } from '@/types/food/client';
import { invariantOf } from '@/utils/invariantOf';
import { parseCheckList } from '@/utils/parseCheckList';

import { useFilterSelectionDisplay } from './useFilterSelectionDisplay';

export const useFoodListFilter = () => {
  const { filterListQueryString } = useFilterSelectionDisplay();
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

  useEffect(() => {
    const newFilterList = Object.entries(invariantOf(filterListQueryString)).reduce(
      (newFilterList, [keyword, queryString]) => ({
        ...newFilterList,
        [keyword]: new Set(queryString?.split(',')),
      }),
      initialSelectedFilterList,
    );

    setSelectedFilterList(newFilterList);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [Object.values(filterListQueryString).join()]);

  return { selectedFilterList, parsedSelectedFilterList, toggleFilter, resetSelectedFilterList };
};
