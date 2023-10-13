import axios, { AxiosError, AxiosRequestConfig } from 'axios';

import { Tokens } from '@/types/auth/client';
import { zipgoLocalStorage } from '@/utils/localStorage';

import { refreshZipgoAuth } from './auth';

export const { BASE_URL } = process.env;

const tokens = zipgoLocalStorage.getTokens();

const defaultConfig: AxiosRequestConfig = {
  baseURL: BASE_URL,
  withCredentials: true,
};

export const createConfigWithAuth = (tokens: Tokens | null) =>
  tokens
    ? {
        ...defaultConfig,
        headers: {
          Authorization: `Bearer ${tokens.accessToken}`,
        },
      }
    : defaultConfig;

export const clientBasic = axios.create(defaultConfig);

export const client = axios.create(createConfigWithAuth(tokens));

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

    if (error?.response?.status === 401) {
      if (!config?.headers.Authorization) {
        alert('로그인 후 이용해 주세요');
      } else {
        try {
          const { accessToken } = await refreshZipgoAuth();

          zipgoLocalStorage.setTokens({ accessToken });

          config.headers.Authorization = `Bearer ${accessToken}`;

          return client(config);
        } catch (error) {
          alert('세션이 만료되었습니다.');

          zipgoLocalStorage.clearAuth();
        }
      }
    }

    return Promise.reject(error);
  },
);
