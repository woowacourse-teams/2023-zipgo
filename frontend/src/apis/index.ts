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
    ? Object.assign(defaultConfig, {
        headers: {
          Authorization: `Bearer ${tokens.accessToken}`,
        },
      })
    : defaultConfig;

export const clientBasic = axios.create(defaultConfig);

export const client = axios.create(createConfigWithAuth(tokens));
