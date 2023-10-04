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
    let newFilterString;

    if (filterListQueryString[category]?.includes(`,${value}`)) {
      newFilterString = filterListQueryString[category]?.replace(`,${value}`, '');
    } else if (filterListQueryString[category]?.includes(`${value},`)) {
      newFilterString = filterListQueryString[category]?.replace(`${value},`, '');
    } else newFilterString = filterListQueryString[category]?.replace(value, '');

    const newQueryString = generateQueryString({
      ...filterListQueryString,
      [category]: newFilterString,
    });

    toggleFilter(category, value);
    replaceQueryString(newQueryString, { exclude: [] });
  };

  return { filterListQueryString, removeFilter };
};
