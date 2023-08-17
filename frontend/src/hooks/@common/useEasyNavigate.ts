import { useNavigate } from 'react-router-dom';

import {
  replaceQueryString as replaceQuery,
  routerPath,
  updateQueryString as updateQuery,
} from '@/router/routes';

const useEasyNavigate = () => {
  const navigate = useNavigate();

  const goHome = () => navigate(routerPath.home());

  const goBack = () => navigate(routerPath.back);

  const updateQueryString = (
    queryString: string,
    options: { path: string } = { path: routerPath.baseUrl() },
  ) => {
    navigate(`${options.path}${updateQuery(queryString)}`);
  };

  const replaceQueryString = (
    queryString: string,
    { path = routerPath.baseUrl(), exclude = [] }: { path?: string; exclude?: string[] },
  ) => {
    navigate(`${path}${replaceQuery(queryString, exclude)}`);
  };

  return { navigate, goHome, goBack, updateQueryString, replaceQueryString };
};

export default useEasyNavigate;
