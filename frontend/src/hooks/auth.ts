import { useEffect } from 'react';

import useValidQueryString from './common/useValidQueryString';
import { useAuthMutation, useAuthQuery } from './query/auth';

export const useAuth = () => {
  const {
    loginZipgoMutation: { loginZipgo },
    logoutKakaoMutation: { logoutKakao },
  } = useAuthMutation();

  return { loginZipgo, logoutKakao };
};
