import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Landing from '@/pages/Landing/Landing';
import Login from '@/pages/Login/Login';

const Router = () => (
  <BrowserRouter>
    <Routes>
      <Route index element={<Landing />} />
      <Route path="/login" element={<Login />} />
      <Route path="/*" element={<div>error</div>} />
    </Routes>
  </BrowserRouter>
);

export default Router;
