import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { ComponentProps, PropsWithChildren, Suspense } from 'react';

import ErrorBoundary from './ErrorBoundary';

interface QueryBoundaryProps {
  errorFallback?: ComponentProps<typeof ErrorBoundary>['fallback'];
  loadingFallback?: ComponentProps<typeof Suspense>['fallback'];
}

const QueryBoundary = (props: PropsWithChildren<QueryBoundaryProps>) => {
  const { children, errorFallback, loadingFallback } = props;

  return (
    <QueryErrorResetBoundary>
      {({ reset }) => (
        <ErrorBoundary onReset={reset} fallback={errorFallback}>
          <Suspense fallback={loadingFallback}>{children}</Suspense>
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default QueryBoundary;
