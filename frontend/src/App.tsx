import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { PropsWithChildren, useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import AxiosInterceptors from './components/@common/AxiosInterceptors/AxiosInterceptors';
import QueryBoundary from './components/@common/QueryBoundary';
import ToastContainer from './components/@common/Toast/ToastContainer';
import useToast from './hooks/toast/useToast';
import { ErrorBoundaryValue } from './types/common/errorBoundary';
import { setScreenSize } from './utils/setScreenSize';

const errorFallback = ({ reset }: ErrorBoundaryValue) => (
  <button type="button" onClick={reset}>
    retry
  </button>
);

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: false,
      useErrorBoundary: true,
    },
    mutations: {
      useErrorBoundary: true,
    },
  },
});

const App = () => (
  <QueryClientProvider client={queryClient}>
    <QueryBoundary errorFallback={errorFallback}>
      <GlobalEvent>
        <AxiosInterceptors>
          <Outlet />
        </AxiosInterceptors>
      </GlobalEvent>
    </QueryBoundary>
  </QueryClientProvider>
);
export default App;

const GlobalEvent = (props: PropsWithChildren) => {
  const { children } = props;

  const { toast, currentToast } = useToast();

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
    <>
      {children}
      <ToastContainer currentToast={currentToast} />;
    </>
  );
};
