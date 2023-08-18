import { useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';

import { User } from '@/types/auth/client';
import { PetProfile } from '@/types/petProfile/client';

import { useAuthMutation, useAuthQuery } from './query/auth';

const QUERY_KEY = { authenticateUser: 'authenticateUser' };

export const useAuth = () => {
  const {
    loginZipgoMutation: { loginZipgo },
    logoutKakaoMutation: { logoutKakao },
  } = useAuthMutation();
  const queryClient = useQueryClient();

  const { isLoggedIn } = useAuthQuery();

  const logout = () => {
    const isLogout = confirm('로그아웃하시겠어요?');

    if (isLogout) {
      localStorage.removeItem('auth');
      localStorage.removeItem('userInfo');
      localStorage.removeItem('petProfile');

      /** @todo 추후 api 추가 후 query hook으로 변경 */
      queryClient.invalidateQueries([QUERY_KEY.authenticateUser]);
    }
  };

  return { isLoggedIn, loginZipgo, logout };
};

export const useUser = () => {
  const accessToken = localStorage.getItem('auth');
  const userData = localStorage.getItem('userInfo');
  const petProfileData = localStorage.getItem('petProfile');

  const [userInfo, setUserInfo] = useState<User | null>(null);
  const [petProfile, setPetProfile] = useState<PetProfile | null>(null);

  useEffect(() => {
    const parsedUserInfo = userData ? (JSON.parse(userData) as User) : null;
    const parsedPetProfile = petProfileData ? (JSON.parse(petProfileData) as PetProfile) : null;

    setUserInfo(parsedUserInfo);
    setPetProfile(parsedPetProfile);
  }, [userData, accessToken]);

  return { userInfo, petProfile, setUserInfo, accessToken };
};
