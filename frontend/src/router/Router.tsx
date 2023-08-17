import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import App from '@/App';
import GlobalStyle from '@/components/@common/GlobalStyle';
import FoodDetail from '@/pages/FoodDetail/FoodDetail';
import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';
import PetProfileAddition from '@/pages/PetProfile/PetProfileAddition';
import PetProfileAgeAddition from '@/pages/PetProfile/PetProfileAgeAddition';
import PetProfileBreedAddition from '@/pages/PetProfile/PetProfileBreedAddition';
import PetProfileEdition from '@/pages/PetProfile/PetProfileEdition';
import PetProfileGenderAddition from '@/pages/PetProfile/PetProfileGenderAddition';
import PetProfileImageAddition from '@/pages/PetProfile/PetProfileImageAddition';
import PetProfileNameAddition from '@/pages/PetProfile/PetProfileNameAddition';
import PetProfilePetSizeAddition from '@/pages/PetProfile/PetProfilePetSizeAddition';
import PetProfileWeightAddition from '@/pages/PetProfile/PetProfileWeightAddition';
import ReviewAddition from '@/pages/ReviewAddition/ReviewAddition';
import ReviewStarRating from '@/pages/ReviewStarRating/ReviewStarRating';
import theme from '@/styles/theme';

import { PATH } from './routes';

export const router = createBrowserRouter([
  {
    path: PATH.HOME,
    element: <App />,
    errorElement: <div>error</div>,
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
        element: <PetProfileAddition />,
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
    <GlobalStyle />
    <RouterProvider router={router} />
  </ThemeProvider>
);

export default Router;
