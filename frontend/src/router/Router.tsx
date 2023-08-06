import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';
import FoodDetail from '@/pages/FoodDetail/FoodDetail';
import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';
import ReviewAddition from '@/pages/ReviewAddition/ReviewAddition';
import ReviewStarRating from '@/pages/ReviewStarRating/ReviewStarRating';

export const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <div>error</div>,
    children: [
      {
        index: true,
        element: <Landing />,
      },
      {
        path: 'login',
        element: <Login />,
      },
      {
        path: '/pet-food/:petFoodId',
        element: <FoodDetail />,
      },
      {
        path: '/pet-food/:petFoodId/reviews/write',
        element: <ReviewStarRating />,
      },
      {
        path: '/pet-food/:petFoodId/reviews/write/detail/:isEditMode',
        element: <ReviewAddition />,
      },
    ],
  },
]);

const Router = () => <RouterProvider router={router} />;

export default Router;
