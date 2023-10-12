import { lazy } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';
import { PetAdditionProvider } from '@/context/petProfile/PetAdditionContext';
import PetProfileProvider from '@/context/petProfile/PetProfileContext';
import ErrorPage from '@/pages/Error/ErrorPage';
import PetProfileEdition from '@/pages/PetProfile/PetProfileEdition/PetProfileEdition';

import { PATH } from './routes';

const Landing = lazy(() => import('@/pages/Landing/Landing'));
const Login = lazy(() => import('@/pages/Login/Login'));
const FoodDetail = lazy(() => import('@/pages/FoodDetail/FoodDetail'));
const ReviewFormFunnel = lazy(() => import('@/pages/Review/ReviewFormFunnel'));
const PetProfileAdditionFormFunnel = lazy(
  () => import('@/pages/PetProfile/PetProfileAddition/PetProfileAdditionFormFunnel'),
);

export const router = createBrowserRouter([
  {
    path: PATH.HOME,
    element: <App />,
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
        path: PATH.REVIEW_ADDITION,
        element: <ReviewFormFunnel />,
      },
      {
        path: PATH.PET_PROFILE_EDITION,
        element: <PetProfileEdition />,
      },
      {
        path: PATH.PET_PROFILE_ADDITION,
        element: (
          <PetAdditionProvider>
            <PetProfileAdditionFormFunnel />
          </PetAdditionProvider>
        ),
      },
      {
        path: PATH.EXCEPTION,
        element: <ErrorPage.NotFound />,
      },
    ],
  },
]);

const Router = () => (
  <PetProfileProvider>
    <RouterProvider router={router} />
  </PetProfileProvider>
);

export default Router;
