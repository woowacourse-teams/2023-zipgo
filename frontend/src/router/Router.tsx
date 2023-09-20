import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import App from '@/App';
import GlobalStyle from '@/components/@common/GlobalStyle';
import { PetAdditionProvider } from '@/context/petProfile/PetAdditionContext';
import PetProfileProvider from '@/context/petProfile/PetProfileContext';
import ToastProvider from '@/context/Toast/ToastContext';
import ErrorPage from '@/pages/Error/ErrorPage';
import FoodDetail from '@/pages/FoodDetail/FoodDetail';
import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';
import PetProfileAddition from '@/pages/PetProfile/PetProfileAddition/PetProfileAddition';
import PetProfileAgeAddition from '@/pages/PetProfile/PetProfileAddition/PetProfileAgeAddition';
import PetProfileBreedAddition from '@/pages/PetProfile/PetProfileAddition/PetProfileBreedAddition';
import PetProfileGenderAddition from '@/pages/PetProfile/PetProfileAddition/PetProfileGenderAddition';
import PetProfileImageAddition from '@/pages/PetProfile/PetProfileAddition/PetProfileImageAddition';
import PetProfileNameAddition from '@/pages/PetProfile/PetProfileAddition/PetProfileNameAddition';
import PetProfilePetSizeAddition from '@/pages/PetProfile/PetProfileAddition/PetProfilePetSizeAddition';
import PetProfileWeightAddition from '@/pages/PetProfile/PetProfileAddition/PetProfileWeightAddition';
import PetProfileEdition from '@/pages/PetProfile/PetProfileEdition/PetProfileEdition';
import ReviewAddition from '@/pages/ReviewAddition/ReviewAddition';
import ReviewStarRating from '@/pages/ReviewStarRating/ReviewStarRating';
import theme from '@/styles/theme';

import { PATH } from './routes';

export const router = createBrowserRouter([
  {
    path: PATH.HOME,
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <Landing />,
      },
      {
        path: PATH.LOGIN,
        element: <Login />,
      },
      {
        path: PATH.FOOD_DETAIL,
        element: <FoodDetail />,
      },
      {
        path: PATH.REVIEW_STAR_RATING,
        element: <ReviewStarRating />,
      },
      {
        path: PATH.REVIEW_ADDITION,
        element: <ReviewAddition />,
      },
      {
        path: PATH.PET_PROFILE_EDITION,
        element: <PetProfileEdition />,
      },
      {
        path: PATH.PET_PROFILE_ADDITION,
        element: (
          <PetAdditionProvider>
            <PetProfileAddition />
          </PetAdditionProvider>
        ),
        children: [
          {
            index: true,
            element: <PetProfileNameAddition />,
          },
          {
            path: PATH.PET_PROFILE_AGE_ADDITION,
            element: <PetProfileAgeAddition />,
          },
          {
            path: PATH.PET_PROFILE_BREED_ADDITION,
            element: <PetProfileBreedAddition />,
          },
          {
            path: PATH.PET_PROFILE_PET_SIZE_ADDITION,
            element: <PetProfilePetSizeAddition />,
          },
          {
            path: PATH.PET_PROFILE_GENDER_ADDITION,
            element: <PetProfileGenderAddition />,
          },
          {
            path: PATH.PET_PROFILE_WEIGHT_ADDITION,
            element: <PetProfileWeightAddition />,
          },
          {
            path: PATH.PET_PROFILE_IMAGE_FILE_ADDITION,
            element: <PetProfileImageAddition />,
          },
        ],
      },
    ],
  },
]);

const Router = () => (
  <ThemeProvider theme={theme}>
    <ToastProvider>
      <PetProfileProvider>
        <GlobalStyle />
        <RouterProvider router={router} />
      </PetProfileProvider>
    </ToastProvider>
  </ThemeProvider>
);

export default Router;
