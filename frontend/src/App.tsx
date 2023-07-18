import { ThemeProvider } from 'styled-components';

import GlobalStyle from '@/components/@common/GlobalStyle';
import { Button } from '@/components/Button/Button';
import theme from '@/styles/theme';

const App = (props: unknown) => (
  <ThemeProvider theme={theme}>
    <GlobalStyle />
    <Button label="hi" />
  </ThemeProvider>
);
export default App;
