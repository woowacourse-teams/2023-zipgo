import { isAxiosError } from 'axios';
import { PropsWithChildren } from 'react';
import { useNavigate } from 'react-router-dom';

import { client } from '@/apis';
import { APIError } from '@/utils/errors';
import { zipgoLocalStorage } from '@/utils/localStorage';

const AxiosInterceptors = (props: PropsWithChildren) => {
  const { children } = props;

  const { request, response } = client.interceptors;

  const navigate = useNavigate();

  if (!request.handlers.length && !response.handlers.length) {
    client.interceptors.request.use(config => {
      const tokens = zipgoLocalStorage.getTokens();

      if (tokens) {
        // eslint-disable-next-line no-param-reassign
        config.headers.Authorization = `Bearer ${tokens.accessToken}`;
      }

      return config;
    });

    client.interceptors.response.use(
      response => response,
      async error => {
        if (!isAxiosError(error) || !APIError.canManage(error)) return error;

        if (error.response.status === 401) {
          alert('세션이 만료되었습니다.');

          zipgoLocalStorage.clearAuth();

          navigate('/login');
        }

        return error;
      },
    );
  }

  return children;
};

export default AxiosInterceptors;
