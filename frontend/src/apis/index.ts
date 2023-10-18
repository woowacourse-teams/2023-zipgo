import axios, { AxiosError, AxiosRequestConfig } from 'axios';

import { APIError } from '@/utils/errors';
import { zipgoLocalStorage } from '@/utils/localStorage';

import { refreshZipgoAuth } from './auth';

export const { BASE_URL } = process.env;

const defaultConfig: AxiosRequestConfig = {
  baseURL: BASE_URL,
};

export const clientBasic = axios.create(defaultConfig);

export const client = axios.create(defaultConfig);

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
  async (error: AxiosError) => {
    const config = error.config;

    if (APIError.isAuthError(error) && config?.headers.Authorization) {
      try {
        const { accessToken } = await refreshZipgoAuth();

        zipgoLocalStorage.setTokens({ accessToken });

        config.headers.Authorization = `Bearer ${accessToken}`;

        return client(config);
      } catch (error) {
        alert('세션이 만료되었습니다');

        zipgoLocalStorage.clearAuth();
      }
    }

    return Promise.reject(error);
  },
);
