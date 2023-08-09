import { useAuthMutation } from './query/auth';

export const useAuth = () => {
  const {
    loginZipgoMutation: { loginZipgo },
    logoutKakaoMutation: { logoutKakao },
  } = useAuthMutation();

  return { loginZipgo, logoutKakao };
};
