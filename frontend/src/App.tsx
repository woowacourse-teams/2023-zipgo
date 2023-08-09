import { useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import GlobalStyle from '@/components/@common/GlobalStyle';
import theme from '@/styles/theme';

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
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <AxiosInterceptors>
        <Outlet />
      </AxiosInterceptors>
    </ThemeProvider>
  );
};
export default App;
