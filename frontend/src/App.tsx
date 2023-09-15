import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { PropsWithChildren, useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import AxiosInterceptors from './components/@common/AxiosInterceptors/AxiosInterceptors';
import QueryBoundary from './components/@common/ErrorBoundary/QueryBoundary/QueryBoundary';
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

    const onUnhandledRejection = () => {
      toast.warning('일시적인 에러가 발생했습니다. 잠시 후 다시 시도해 주세요!');
    };

    const onOffline = () => {
      toast.warning('네트워크 상태를 확인해 주세요!');
    };

    const onOnline = () => {
      toast.success('네트워크가 연결되었습니다!');
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

  return (
    <>
      {children}
      <ToastContainer currentToast={currentToast} />;
    </>
  );
};
