import { useEffect } from 'react';
import { ThemeProvider } from 'styled-components';

import GlobalStyle from '@/components/@common/GlobalStyle';
import theme from '@/styles/theme';

import Router from './router/Router';
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
      <Router />
    </ThemeProvider>
  );
};
export default App;
