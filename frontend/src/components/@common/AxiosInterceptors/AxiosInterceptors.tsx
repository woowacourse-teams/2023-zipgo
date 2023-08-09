import { isAxiosError } from 'axios';
import { PropsWithChildren, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { client } from '@/apis';

const AxiosInterceptors = (props: PropsWithChildren) => {
  const { children } = props;

  const navigate = useNavigate();

  useEffect(() => {
    const requestInterceptor = client.interceptors.request.use(config => {
      const accessToken = localStorage.getItem('auth');

      if (accessToken) {
        // eslint-disable-next-line no-param-reassign
        config.headers.Authorization = `Bearer ${accessToken}`;
      }

      return config;
    });

    const responseInterceptor = client.interceptors.response.use(
      response => response,
      async error => {
        if (!isAxiosError(error)) return error;

        if (error.response && error.response.status === 401) {
          alert('세션이 만료되었습니다.');
          navigate('/login');
        }

        return error;
      },
    );

    return () => {
      client.interceptors.request.eject(requestInterceptor);
      client.interceptors.response.eject(responseInterceptor);
    };
  }, []);

  return children;
};

export default AxiosInterceptors;
