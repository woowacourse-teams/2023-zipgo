import { useCallback, useEffect, useRef, useState } from 'react';

import { initialSelectedFilterList } from '@/context/food';
import type { KeywordEn } from '@/types/food/client';
import { parseCheckList } from '@/utils/parseCheckList';

import useValidQueryString from './common/useValidQueryString';
import { useFoodListInfiniteQuery } from './query/food';

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

export const useInfiniteFoodListScroll = () => {
  const queries = useValidQueryString<KeywordEn>([
    'nutritionStandards',
    'mainIngredients',
    'brands',
    'functionalities',
  ]);

  const queriesString = Object.values(queries).join();

  const {
    foodList,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    remove,
    refetch,
    ...restQuery
  } = useFoodListInfiniteQuery(queries);

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
  }, [queriesString, refetch, remove]);

  return { foodList, hasNextPage, refetch, targetRef, ...restQuery };
};
