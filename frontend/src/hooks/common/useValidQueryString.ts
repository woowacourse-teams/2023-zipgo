import { useLocation } from 'react-router-dom';

import { getValidQueries } from '@/utils/getValidQueries';

const useValidQueryString = <T extends string>(
  queryKeys: Readonly<T[]>,
): Record<T, string | undefined> => getValidQueries(useLocation().search, queryKeys);

export default useValidQueryString;
