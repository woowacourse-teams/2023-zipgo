import axios from 'axios';

import { Tokens } from '@/types/auth/client';
import { zipgoLocalStorage } from '@/utils/localStorage';

export const { BASE_URL } = process.env;

const tokens = zipgoLocalStorage.getTokens();

const defaultConfig = {
  baseURL: BASE_URL,
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
  async error => {
    if (error.response.status === 401) {
      alert('세션이 만료되었습니다.');

      zipgoLocalStorage.clearAuth();
    }

    return Promise.reject(error);
  },
);
