import { ComponentProps, PropsWithChildren } from 'react';
import { Navigate } from 'react-router-dom';

import { PATH } from '@/router/routes';
import { resolveRenderProps } from '@/utils/compound';
import { APIError, ZipgoError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

type APIBoundaryProps = ComponentProps<typeof ErrorBoundary<APIError>>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => {
  const { fallback, ...restProps } = props;

  const handleAPIError: APIBoundaryProps['onError'] = error => {
    const convertedError = ZipgoError.convertToError(error);

    if (!(convertedError instanceof APIError)) throw convertedError;
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
