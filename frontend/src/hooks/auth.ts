import { useQueryClient } from '@tanstack/react-query';

import { zipgoLocalStorage } from '@/utils/localStorage';

import { useAuthMutation, useAuthQuery } from './query/auth';

const QUERY_KEY = { authenticateUser: 'authenticateUser' };

export const useAuth = () => {
  const {
    loginZipgoMutation: { loginZipgo },
    logoutKakaoMutation: { logoutKakao },
  } = useAuthMutation();
  const queryClient = useQueryClient();

  const logout = () => {
    const isLogout = confirm('로그아웃하시겠어요?');

    if (isLogout) {
      zipgoLocalStorage.clearAuth();

      /** @todo 추후 api 추가 후 query hook으로 변경 */
      queryClient.invalidateQueries([QUERY_KEY.authenticateUser]);
    }
  };

  return { loginZipgo, logout };
};

export const useCheckAuth = () => useAuthQuery();
