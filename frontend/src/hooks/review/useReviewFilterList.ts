import { ChangeEvent, useEffect, useState } from 'react';

import { parseCheckList } from '@/utils/parseCheckList';

import useValidQueryString from '../common/useValidQueryString';

type FilterMeta = {
  id: number;
  name: string;
}[];

interface MetaData {
  petSizes: FilterMeta;
  ageGroups: FilterMeta;
  breeds: FilterMeta;
}

const initialFilterList: Record<keyof MetaData, Set<number>> = {
  petSizes: new Set(),
  ageGroups: new Set(),
  breeds: new Set(),
};

const useReviewFilterList = () => {
  const { ageGroups, breeds, custom } = useValidQueryString(['ageGroups', 'breeds', 'custom']);
  const [filterList, setFilterList] = useState(initialFilterList);

  const parsedFilterList = parseCheckList(filterList);

  const toggleFilter = (keyword: keyof MetaData, filterId: number) => {
    const targetFilterList = structuredClone(filterList)[keyword];
    const selected = targetFilterList.has(filterId);

    selected ? targetFilterList.delete(filterId) : targetFilterList.add(filterId);

    setFilterList(prev => ({ ...prev, [keyword]: new Set(targetFilterList) }));
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
    if (custom === 'on') {
      setFilterList(prev => ({
        petSizes: new Set(),
        ageGroups: new Set([Number(ageGroups)]),
        breeds: new Set([Number(breeds)]),
      }));

      return;
    }

    setFilterList(prev => ({
      petSizes: new Set(),
      ageGroups: new Set(),
      breeds: new Set(),
    }));
  }, [custom]);

  return { filterList, parsedFilterList, toggleFilter, onSelectBreed, resetFilterList };
};

export default useReviewFilterList;
