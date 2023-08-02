import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';
import FoodDetail from '@/pages/FoodDetail/FoodDetail';
import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';

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
        path: 'pet-food/:petFoodId',
        element: <FoodDetail />,
      },
    ],
  },
]);

const Router = () => <RouterProvider router={router} />;

export default Router;
