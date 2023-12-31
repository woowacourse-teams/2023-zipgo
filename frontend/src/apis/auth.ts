import { KAKAO_REDIRECT_URI } from '@/constants/auth';
import {
  AuthenticateUserRes,
  LoginKakaoAuthRes,
  LoginZipgoAuthReq,
  LoginZipgoAuthRes,
  RefreshZipgoAuthRes,
} from '@/types/auth/remote';
import { zipgoLocalStorage } from '@/utils/localStorage';

import { client, clientBasic } from '.';

export const loginZipgoAuth = async ({ code }: LoginZipgoAuthReq) => {
  const { data } = await client.post<LoginZipgoAuthRes>('/auth/login', null, {
    params: {
      code,
      'redirect-uri': KAKAO_REDIRECT_URI,
    },
  });

  return data;
};

export const refreshZipgoAuth = async () => {
  const { refreshToken } = zipgoLocalStorage.getTokens({ required: true });

  const {
    data: { accessToken },
  } = await clientBasic<RefreshZipgoAuthRes>('/auth/refresh', {
    headers: {
      Refresh: `Zipgo ${refreshToken}`,
    },
  });

  return {
    accessToken,
    refreshToken,
  };
};

export const loginKakaoAuth = async () => {
  const { data } = await client<LoginKakaoAuthRes>('/login/kakao');

  return data;
};

export const logoutKaKaoAuth = async () => {
  const { data } = await client.post<LoginKakaoAuthRes>(
    'https://kapi.kakao.com/v1/user/logout',
    null,
    {
      headers: {
        /** @todo kakao logout 여부 논의 */
        Authorization: `Bearer ${localStorage.getItem('kakao')}`,
      },
    },
  );

  return data;
};

export const authenticateUser = async () => {
  const tokens = zipgoLocalStorage.getTokens();

  const { data } = await client<AuthenticateUserRes>('/auth', {
    headers: {
      Refresh: `Zipgo ${tokens?.refreshToken}`,
    },
  });

  return data;
};
