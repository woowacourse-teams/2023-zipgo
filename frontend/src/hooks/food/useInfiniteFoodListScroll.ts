import { useCallback, useEffect, useRef } from 'react';

import { KEYWORD_EN } from '@/constants/food';
import useValidQueryString from '@/hooks/common/useValidQueryString';
import { useFoodListInfiniteQuery } from '@/hooks/query/food';
import type { KeywordEn } from '@/types/food/client';

export const useInfiniteFoodListScroll = () => {
  const queries = useValidQueryString<KeywordEn>(KEYWORD_EN);

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
