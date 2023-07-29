import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';

import App from './App';
import QueryBoundary from './components/@common/QueryBoundary';
import { startWorker } from './mocks/worker';
import { ErrorBoundaryValue } from './types/common/errorBoundary';

startWorker();

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: 0,
      useErrorBoundary: true,
    },
    mutations: {
      useErrorBoundary: true,
    },
  },
});

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

const loadingFallback = <div>loading...</div>;
const errorFallback = ({ reset }: ErrorBoundaryValue) => (
  <button type="button" onClick={reset}>
    retry
  </button>
);

root.render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <QueryBoundary loadingFallback={loadingFallback} errorFallback={errorFallback}>
        <App />
      </QueryBoundary>
    </QueryClientProvider>
  </React.StrictMode>,
);
