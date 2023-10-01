import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import AxiosInterceptors from './components/@common/AxiosInterceptors/AxiosInterceptors';
import { EndOfErrorBoundary } from './components/@common/ErrorBoundary/ErrorBoundary';
import QueryBoundary from './components/@common/ErrorBoundary/QueryBoundary/QueryBoundary';
import GlobalStyle from './components/@common/GlobalStyle';
import { ERROR_MESSAGE_KIT } from './constants/errors';
import { ONE_HOUR } from './constants/time';
import ToastProvider, { useToast } from './context/Toast/ToastContext';
import useErrorBoundary from './hooks/@common/useErrorBoundary';
import ErrorPage from './pages/Error/ErrorPage';
import theme from './styles/theme';
import { ErrorBoundaryValue } from './types/common/errorBoundary';
import { UnexpectedError } from './utils/errors';
import { setScreenSize } from './utils/setScreenSize';

const errorFallback = ({ reset, error }: ErrorBoundaryValue) => (
  <ErrorPage reset={reset} error={error} />
);

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: false,
      useErrorBoundary: true,
      staleTime: ONE_HOUR,
      cacheTime: ONE_HOUR,
    },
  },
});

const App = () => (
  <ThemeProvider theme={theme}>
    <GlobalStyle />
    <QueryClientProvider client={queryClient}>
      <EndOfErrorBoundary fallback={errorFallback}>
        <QueryBoundary errorFallback={errorFallback}>
          <ToastProvider>
            <AxiosInterceptors>
              <GlobalEvent />
              <Outlet />
            </AxiosInterceptors>
          </ToastProvider>
        </QueryBoundary>
      </EndOfErrorBoundary>
    </QueryClientProvider>
  </ThemeProvider>
);
export default App;

const GlobalEvent = () => {
  const { toast } = useToast();

  const { setError } = useErrorBoundary();

  useEffect(() => {
    setScreenSize();
  }, []);

  useEffect(() => {
    const onResize = () => {
      setScreenSize();
    };

    const onUnhandledRejection = () => {
      setError(new UnexpectedError('Unhandled rejection'));
    };

    const onOffline = () => {
      toast.warning(ERROR_MESSAGE_KIT.OFFLINE);
    };

    const onOnline = () => {
      toast.success(ERROR_MESSAGE_KIT.ONLINE);
    };

    window.addEventListener('resize', onResize);
    window.addEventListener('online', onOnline);
    window.addEventListener('offline', onOffline);
    window.addEventListener('unhandledrejection', onUnhandledRejection);

    return () => {
      window.removeEventListener('resize', onResize);
      window.removeEventListener('online', onOnline);
      window.removeEventListener('offline', onOffline);
      window.removeEventListener('unhandledrejection', onUnhandledRejection);
    };
  }, []);

  return null;
};
