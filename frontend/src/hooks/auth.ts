import { useEffect, useState } from 'react';

import { User } from '@/types/auth/client';

import { useAuthMutation } from './query/auth';

export const useAuth = () => {
  const {
    loginZipgoMutation: { loginZipgo },
    logoutKakaoMutation: { logoutKakao },
  } = useAuthMutation();

  const logout = () => {
    const isLogout = confirm('로그아웃하시겠어요?');

    if (isLogout) {
      localStorage.removeItem('auth');
      localStorage.removeItem('userInfo');
    }
  };

  return { loginZipgo, logout };
};

export const useUser = () => {
  const accessToken = localStorage.getItem('auth');
  const userData = localStorage.getItem('userInfo');

  const [userInfo, setUserInfo] = useState<User | null>(null);

  useEffect(() => {
    const parsedUserInfo = userData ? (JSON.parse(userData) as User) : null;
    setUserInfo(parsedUserInfo);
  }, [userData, accessToken]);

  return { userInfo, setUserInfo, accessToken };
};
