import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';
import ReviewList from '@/components/Review/ReviewList/ReviewList';
import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';
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
        path: 'pet-foods/:petFoodId/reviews',
        element: <ReviewList />,
      },
      {
        path: 'pet-foods/:petFoodId/reviews/write',
        element: <ReviewStarRating />,
      },
    ],
  },
]);

const Router = () => <RouterProvider router={router} />;

export default Router;
