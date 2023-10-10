import { KEYWORD_EN } from '@/constants/food';
import { generateQueryString } from '@/router/routes';
import { KeywordEn } from '@/types/food/client';

import useEasyNavigate from '../@common/useEasyNavigate';
import useValidQueryString from '../common/useValidQueryString';

export const useFilterSelectionDisplay = () => {
  const { replaceQueryString } = useEasyNavigate();
  const filterListQueryString = useValidQueryString(KEYWORD_EN);

  const removeFilter = (keyword: KeywordEn, value: string) => {
    const filterList = filterListQueryString[keyword]?.split(',');

    if (!filterList) return;

    if (filterList.includes(value)) {
      filterList.splice(filterList.indexOf(value), 1);

      const updatedQueryString = filterList.join(',');
      const newQueryString = generateQueryString({
        ...filterListQueryString,
        [keyword]: updatedQueryString,
      });

      replaceQueryString(newQueryString, { exclude: [] });
    }
  };

  return { filterListQueryString, removeFilter };
};
