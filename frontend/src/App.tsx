import { useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import AxiosInterceptors from './components/@common/AxiosInterceptors/AxiosInterceptors';
import { setScreenSize } from './utils/setScreenSize';

const App = () => {
  useEffect(() => {
    setScreenSize();
  }, []);

  useEffect(() => {
    const onResize = () => {
      setScreenSize();
    };

    window.addEventListener('resize', onResize);

    return () => {
      window.removeEventListener('resize', onResize);
    };
  }, []);

  return (
    <AxiosInterceptors>
      <Outlet />
    </AxiosInterceptors>
  );
};
export default App;
