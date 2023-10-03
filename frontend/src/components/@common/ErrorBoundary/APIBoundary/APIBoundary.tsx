import { ComponentProps, PropsWithChildren } from 'react';
import { Navigate } from 'react-router-dom';

import { PATH } from '@/router/routes';
import { resolveRenderProps } from '@/utils/compound';
import { APIError, UnexpectedError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

type APIBoundaryProps = ComponentProps<typeof ErrorBoundary<APIError>>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => {
  const { fallback, ...restProps } = props;

  const handleAPIError: APIBoundaryProps['onError'] = (error: APIError | UnexpectedError) => {
    if (!(error instanceof APIError)) throw error;
  };

  const handleAPIFallback: APIBoundaryProps['fallback'] = ({ error, reset }) =>
    /** @todo 추후 에러 코드 상의 후 변경 */
    error.status === 401 ? (
      <Navigate to={PATH.LOGIN} />
    ) : (
      resolveRenderProps(fallback, { error, reset })
    );

  return (
    <ErrorBoundary<APIError> onError={handleAPIError} fallback={handleAPIFallback} {...restProps} />
  );
};

export type { APIBoundaryProps };

export default APIBoundary;
