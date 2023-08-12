import { useCallback, useEffect, useRef, useState } from 'react';

import { initialSelectedFilterList, SelectedFilterList } from '@/context/food';
import type { KeywordEn } from '@/types/food/client';
import { isEqualObjectWithStringValues } from '@/utils/objectComparator';

import useValidQueryString from './common/useValidQueryString';
import { useFoodListInfiniteQuery } from './query/food';

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

export const useInfiniteFoodListScroll = () => {
  const queries = useValidQueryString<KeywordEn>([
    'nutritionStandards',
    'mainIngredients',
    'brands',
    'functionalities',
  ]);

  if (isEqualObjectWithStringValues<KeywordEn>(queries, {})) queries.nutritionStandards = '';

  const { foodList, fetchNextPage, hasNextPage, isFetchingNextPage, remove, refetch } =
    useFoodListInfiniteQuery(queries);

  const executeFoodListInfiniteQuery = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      entries.forEach(entry => {
        const canLoadMore = entry.isIntersecting && hasNextPage && !isFetchingNextPage;

        if (canLoadMore) fetchNextPage();
      });
    },
    [hasNextPage, isFetchingNextPage, fetchNextPage],
  );

  const targetRef = useRef(null);

  useEffect(() => {
    const observer = new IntersectionObserver(executeFoodListInfiniteQuery, { threshold: 0.1 });

    if (targetRef.current) observer.observe(targetRef.current);

    return () => observer.disconnect();
  }, [executeFoodListInfiniteQuery]);

  useEffect(() => {
    remove();
    refetch();
  }, Object.values(queries));

  return { foodList, hasNextPage, refetch, targetRef };
};
