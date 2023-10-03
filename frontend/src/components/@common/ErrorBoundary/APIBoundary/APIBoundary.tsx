import { ComponentProps, PropsWithChildren } from 'react';
import { Navigate } from 'react-router-dom';

import { PATH } from '@/router/routes';
import { resolveRenderProps } from '@/utils/compound';
import { composeFunctions } from '@/utils/dom';
import { APIError, UnexpectedError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

type APIBoundaryProps = ComponentProps<typeof ErrorBoundary<APIError>>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => {
  const { fallback, onError, ...restProps } = props;

  const handleAPIError: APIBoundaryProps['onError'] = ({
    error,
  }: {
    error: APIError | UnexpectedError;
  }) => {
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
    <ErrorBoundary<APIError>
      onError={composeFunctions(handleAPIError, onError)}
      fallback={handleAPIFallback}
      {...restProps}
    />
  );
};

export type { APIBoundaryProps };

export default APIBoundary;
