import { ComponentProps, PropsWithChildren } from 'react';
import { Navigate } from 'react-router-dom';

import { PATH } from '@/router/routes';
import { resolveRenderProps } from '@/utils/compound';
import { APIError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

type APIBoundaryProps = ComponentProps<typeof ErrorBoundary<APIError>>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => {
  const { fallback, ...restProps } = props;

  const handleIgnore: APIBoundaryProps['shouldIgnore'] = ({ error }) =>
    !(error instanceof APIError);

  const handleAPIFallback: APIBoundaryProps['fallback'] = ({ error, reset }) =>
    /** @todo 추후 에러 코드 상의 후 변경 */
    error.status === 401 ? (
      <Navigate to={PATH.LOGIN} />
    ) : (
      resolveRenderProps(fallback, { error, reset })
    );

  return (
    /** @description ignore는 사용하는 쪽에서 재정의 할 수 있다 */
    <ErrorBoundary<APIError>
      shouldIgnore={handleIgnore}
      fallback={handleAPIFallback}
      {...restProps}
    />
  );
};

export type { APIBoundaryProps };

export default APIBoundary;
