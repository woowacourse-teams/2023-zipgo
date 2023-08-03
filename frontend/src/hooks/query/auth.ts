import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

import { loginKakaoAuth, loginZipgoAuth, logoutKaKaoAuth } from '@/apis/auth';

const QUERY_KEY = { kakaoAuth: 'kakaoAuth' };

export const useAuthQuery = () => {
  const queryClient = useQueryClient();

  const getKakaoAuth = () => {
    queryClient.fetchQuery({
      queryKey: [QUERY_KEY.kakaoAuth],
      queryFn: loginKakaoAuth,
    });
  };

  return {
    getKakaoAuth,
  };
};

export const useAuthMutation = () => {
  const navigate = useNavigate();

  const { mutate: loginZipgo, ...loginRestMutation } = useMutation({
    mutationFn: loginZipgoAuth,
    onSuccess({ accessToken }) {
      localStorage.setItem('auth', accessToken);
      navigate('/');
    },
  });

  const { mutate: logoutKakao, ...logoutRestMutation } = useMutation({
    mutationFn: logoutKaKaoAuth,
  });

  return {
    loginZipgoMutation: { loginZipgo, ...loginRestMutation },
    logoutKakaoMutation: { logoutKakao, ...logoutRestMutation },
  };
};
