import { useQueryClient } from '@tanstack/react-query';

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
