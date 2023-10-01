import { ComponentProps, PropsWithChildren } from 'react';

import { APIError, ZipgoError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

type APIBoundaryProps = ComponentProps<typeof ErrorBoundary>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => {
  const handleAPIError = (error: unknown) => {
    const convertedError = ZipgoError.convertToError(error);

    if (!(convertedError instanceof APIError)) throw convertedError;
  };

  return <ErrorBoundary onError={handleAPIError} {...props} />;
};

export type { APIBoundaryProps };

export default APIBoundary;
