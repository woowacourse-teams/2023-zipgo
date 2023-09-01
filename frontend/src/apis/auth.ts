import {
  AuthenticateUserRes,
  LoginKakaoAuthRes,
  LoginZipgoAuthReq,
  LoginZipgoAuthRes,
} from '@/types/auth/remote';

import { client, clientBasic, createConfigWithAuth } from '.';

export const loginZipgoAuth = async ({ code }: LoginZipgoAuthReq) => {
  const { data } = await client.post<LoginZipgoAuthRes>('/auth/login', null, {
    params: {
      code,
    },
  });

  return data;
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
        Authorization: `Bearer ${localStorage.getItem('kakao')}`,
      },
    },
  );

  return data;
};

export const authenticateUser = async () => {
  const tokens = zipgoLocalStorage.getTokens();

  const { data } = await clientBasic.get<AuthenticateUserRes>(
    '/auth',
    createConfigWithAuth(tokens),
  );

  return data;
};
