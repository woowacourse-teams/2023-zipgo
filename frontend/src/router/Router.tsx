import { ReactNode } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';
import FoodDetail from '@/pages/FoodDetail/FoodDetail';
import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';
import PetProfileAddition from '@/pages/PetProfile/PetProfileAddition';
import PetProfileAgeAddition from '@/pages/PetProfile/PetProfileAgeAddition';
import PetProfileBreedsAddition from '@/pages/PetProfile/PetProfileBreedsAddition';
import PetProfileBreedsSizeAddition from '@/pages/PetProfile/PetProfileBreedsSizeAddition';
import PetProfileGenderAddition from '@/pages/PetProfile/PetProfileGenderAddition';
import PetProfileNameAddition from '@/pages/PetProfile/PetProfileNameAddition';
import ReviewAddition from '@/pages/ReviewAddition/ReviewAddition';
import ReviewStarRating from '@/pages/ReviewStarRating/ReviewStarRating';

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
            path: PATH.PET_PROFILE_BREEDS_ADDITION,
            element: <PetProfileBreedsAddition />,
          },
          {
            path: PATH.PET_PROFILE_BREEDS_SIZE_ADDITION,
            element: <PetProfileBreedsSizeAddition />,
          },
          {
            path: PATH.PET_PROFILE_GENDER_ADDITION,
            element: <PetProfileGenderAddition />,
          },
        ],
      },
    ],
  },
]);

const Router = () => <RouterProvider router={router} />;

export default Router;
