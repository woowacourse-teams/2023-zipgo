import { ComponentProps, PropsWithChildren } from 'react';

import { ZipgoError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

export type APIBoundaryProps = ComponentProps<typeof ErrorBoundary>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => {
  const handleAPIError = (error: unknown) => {
    throw ZipgoError.convertToError(error);
  };

  return <ErrorBoundary onError={handleAPIError} {...props} />;
};

export default APIBoundary;
