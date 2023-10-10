import { KEYWORD_EN } from '@/constants/food';
import { useFoodFilterContext } from '@/context/food';
import { generateQueryString } from '@/router/routes';
import { KeywordEn } from '@/types/food/client';

import useEasyNavigate from '../@common/useEasyNavigate';
import useValidQueryString from '../common/useValidQueryString';

export const useFilterSelectionDisplay = () => {
  const { replaceQueryString } = useEasyNavigate();
  const { toggleFilter } = useFoodFilterContext();
  const filterListQueryString = useValidQueryString(KEYWORD_EN);

  const removeFilter = (category: KeywordEn, value: string) => {
    const filterList = filterListQueryString[category]?.split(',');

    if (!filterList) return;

    if (filterList.includes(value)) {
      filterList.splice(filterList.indexOf(value), 1);

      const updatedQueryString = filterList.join(',');
      const newQueryString = generateQueryString({
        ...filterListQueryString,
        [category]: updatedQueryString,
      });

      replaceQueryString(newQueryString, { exclude: [] });

      toggleFilter(category, value);
    }
  };

  return { filterListQueryString, removeFilter };
};
