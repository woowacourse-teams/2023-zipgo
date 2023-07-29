import { useEffect } from 'react';

import useValidQueryString from './common/useValidQueryString';
import { useAuthMutation, useAuthQuery } from './query/auth';

export const useAuth = () => {
  const {
    code,
    error,
    error_description: errorDescription,
  } = useValidQueryString(['code', 'error', 'error_description']);
  const { getKakaoAuth } = useAuthQuery();
  const {
    loginZipgoMutation: { loginZipgo },
    logoutKakaoMutation: { logoutKakao },
  } = useAuthMutation();

  useEffect(() => {
    if (error) throw new Error(errorDescription);

    if (code) {
      loginZipgo({ code });
    }
  }, [code, error, errorDescription]);

  return { getKakaoAuth, logoutKakao };
};
