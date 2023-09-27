import { ComponentProps, PropsWithChildren } from 'react';

import { CustomError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

export type APIBoundaryProps = ComponentProps<typeof ErrorBoundary>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => {
  const handleAPIError = (error: unknown) => {
    throw CustomError.convertToError(error);
  };

  return <ErrorBoundary onError={handleAPIError} {...props} />;
};

export default APIBoundary;
