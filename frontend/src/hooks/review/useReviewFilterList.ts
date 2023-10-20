import { ChangeEvent, useEffect, useState } from 'react';

import { REVIEW_FILTER_QUERY_STRINGS } from '@/constants/review';
import { FilterControlsMeta } from '@/types/review/client';
import { invariantOf } from '@/utils/invariantOf';
import { parseCheckList } from '@/utils/parseCheckList';

import useValidQueryString from '../@common/useValidQueryString';

const initialFilterList: Record<keyof FilterControlsMeta, Set<number>> = {
  petSizes: new Set(),
  ageGroups: new Set(),
  breeds: new Set(),
};

const useReviewFilterList = () => {
  const filterListQueryString = useValidQueryString([...REVIEW_FILTER_QUERY_STRINGS, 'custom']);
  const [filterList, setFilterList] = useState(initialFilterList);

  const { ageGroups, breeds, custom } = filterListQueryString;

  const parsedFilterList = parseCheckList(filterList);

  const toggleFilter = (keyword: keyof FilterControlsMeta, filterId: number) => {
    const targetFilterList = structuredClone(filterList)[keyword];
    const selected = targetFilterList.has(filterId);

    selected ? targetFilterList.delete(filterId) : targetFilterList.add(filterId);

    setFilterList(prev => ({ ...prev, [keyword]: new Set(targetFilterList) }));
  };

  const toggleCustomMode = () => {
    if (custom === 'on') {
      setFilterList({
        petSizes: new Set(),
        ageGroups: new Set([Number(ageGroups)]),
        breeds: new Set([Number(breeds)]),
      });
    } else {
      resetFilterList();
    }
  };

  const resetFilterList = () => setFilterList(initialFilterList);

  const onSelectBreed = (e: ChangeEvent<HTMLSelectElement>) => {
    const breeds =
      Number(e.target.value) === -1
        ? new Set<number>()
        : new Set<number>().add(Number(e.target.value));

    setFilterList(prev => ({ ...prev, breeds }));
  };

  useEffect(() => {
    toggleCustomMode();
  }, [custom]);

  useEffect(() => {
    const syncedFilterList = Object.entries(invariantOf(filterListQueryString)).reduce(
      (newFilterList, [keyword, queryString]) => ({
        ...newFilterList,
        [keyword]: new Set(queryString?.split(',').map(Number)),
      }),
      initialFilterList,
    );

    setFilterList(syncedFilterList);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [Object.values(filterListQueryString).join()]);

  return { filterList, parsedFilterList, toggleFilter, onSelectBreed, resetFilterList };
};

export default useReviewFilterList;
