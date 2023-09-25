import { AxiosError, isAxiosError } from 'axios';
import { ComponentProps, PropsWithChildren } from 'react';

import { APIError, ManageableAxiosError, UnexpectedError } from '@/utils/errors';

import { ErrorBoundary } from '../ErrorBoundary';

export type APIBoundaryProps = ComponentProps<typeof ErrorBoundary>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => (
  <ErrorBoundary
    onError={error => {
      if (!isAxiosError(error) || !canManage(error)) {
        throw new UnexpectedError(error);
      }

      throw new APIError(error);
    }}
    {...props}
  />
);

/**
 * @description `No Internet` 과 `Server not reachable' 구분이 되지 않지만 online event로 핸들링한다
 */
const canManage = <T, D>(error: AxiosError<T, D>): error is ManageableAxiosError<typeof error> =>
  error.config && error.request && error.response && error.config.method === 'get';

export default APIBoundary;
