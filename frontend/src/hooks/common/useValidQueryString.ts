import { useLocation } from 'react-router-dom';

import { getValidQueries } from '@/utils/getValidQueries';

const useValidQueryString = <T extends string>(
  queryKeys: Readonly<T[]>,
): Partial<Record<T, string>> => getValidQueries(useLocation().search, queryKeys);

export default useValidQueryString;
