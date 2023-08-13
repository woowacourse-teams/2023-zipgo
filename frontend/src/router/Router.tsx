import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';
import FoodDetail from '@/pages/FoodDetail/FoodDetail';
import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';
import PetProfileAddition from '@/pages/PetProfile/PetProfileAddition';
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
        ],
      },
    ],
  },
]);

const Router = () => <RouterProvider router={router} />;

export default Router;
