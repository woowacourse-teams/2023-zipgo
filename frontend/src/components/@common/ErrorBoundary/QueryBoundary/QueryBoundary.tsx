import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { ComponentProps, PropsWithChildren, Suspense } from 'react';

import { composeFunctions } from '@/utils/dom';

import LoadingSpinner from '../../LoadingSpinner';
import APIBoundary from '../APIBoundary/APIBoundary';

interface QueryBoundaryProps extends Omit<ComponentProps<typeof APIBoundary>, 'fallback'> {
  errorFallback?: ComponentProps<typeof APIBoundary>['fallback'];
  loadingFallback?: ComponentProps<typeof Suspense>['fallback'];
}

const QueryBoundary = (props: PropsWithChildren<QueryBoundaryProps>) => {
  const {
    children,
    loadingFallback = <LoadingSpinner />,
    errorFallback,
    onReset,
    ...restProps
  } = props;

  return (
    <QueryErrorResetBoundary>
      {({ reset }) => (
        <APIBoundary
          fallback={errorFallback}
          onReset={composeFunctions<void>(reset, onReset)}
          {...restProps}
        >
          <Suspense fallback={loadingFallback}>{children}</Suspense>
        </APIBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default QueryBoundary;
