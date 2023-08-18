import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

import { authenticateUser, loginZipgoAuth, logoutKaKaoAuth } from '@/apis/auth';
import { routerPath } from '@/router/routes';

const QUERY_KEY = { authenticateUser: 'authenticateUser' };

export const useAuthQuery = () => {
  const { isSuccess } = useQuery({
    queryKey: [QUERY_KEY.authenticateUser],
    queryFn: authenticateUser,
    useErrorBoundary: false,
    refetchOnWindowFocus: false,
  });

  return {
    isLoggedIn: isSuccess,
  };
};

export const useAuthMutation = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { mutate: loginZipgo, ...loginRestMutation } = useMutation({
    mutationFn: loginZipgoAuth,
    onSuccess({ accessToken, authResponse }) {
      localStorage.setItem('auth', accessToken);
      localStorage.setItem('userInfo', JSON.stringify(authResponse));

      queryClient.invalidateQueries([QUERY_KEY.authenticateUser]);

      navigate(routerPath.home());
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
