import { AxiosError, isAxiosError } from 'axios';
import { ComponentProps, PropsWithChildren } from 'react';

import ErrorBoundary from '../ErrorBoundary';

type ManageableStandard = 'config' | 'request' | 'response';

type StandardInfo<E extends AxiosError> = Pick<E, ManageableStandard>;

type ManageableAxiosError<E extends AxiosError> = {
  [key in keyof StandardInfo<E>]-?: StandardInfo<E>[key];
} & Omit<E, ManageableStandard>;

export type APIBoundaryProps = ComponentProps<typeof ErrorBoundary>;

const APIBoundary = (props: PropsWithChildren<APIBoundaryProps>) => (
  <ErrorBoundary
    onError={error => {
      if (!isAxiosError(error) || !canManage(error)) {
        // eslint-disable-next-line no-throw-literal
        throw { ...error, ignore: true };
      }
      /** @description 통신 성공 */
      /**
       * @todo custom error code 추가 후 에러 핸들링
       */
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
