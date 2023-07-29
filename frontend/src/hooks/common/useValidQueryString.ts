import { useLocation } from 'react-router-dom';

import { getValidQueries } from '@/utils/getValidQueries';

const useValidQueryString = <T extends string>(
  keyArr: Readonly<T[]>,
): Record<T, string | undefined> => getValidQueries(useLocation().search, keyArr);

export default useValidQueryString;
