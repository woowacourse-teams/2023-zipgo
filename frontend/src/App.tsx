import { useEffect } from 'react';
import { ThemeProvider } from 'styled-components';

import GlobalStyle from '@/components/@common/GlobalStyle';
import theme from '@/styles/theme';

import Landing from './pages/Landing/Landing';
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
      <Landing />
    </ThemeProvider>
  );
};
export default App;
