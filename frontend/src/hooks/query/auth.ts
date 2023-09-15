import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

import { authenticateUser, loginZipgoAuth, logoutKaKaoAuth } from '@/apis/auth';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { routerPath } from '@/router/routes';
import { zipgoLocalStorage } from '@/utils/localStorage';

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
  const { updatePetProfile } = usePetProfile();

  const { mutate: loginZipgo, ...loginRestMutation } = useMutation({
    mutationFn: loginZipgoAuth,
    onSuccess({ accessToken, authResponse }) {
      const newestPet = authResponse.pets.at(-1);
      const selectedPet = zipgoLocalStorage.getPetProfile();

      zipgoLocalStorage.setTokens({ accessToken });
      zipgoLocalStorage.setUserInfo(authResponse);

      if (selectedPet) updatePetProfile(selectedPet);
      else if (newestPet) updatePetProfile(newestPet);

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
