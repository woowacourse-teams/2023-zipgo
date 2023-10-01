/* eslint-disable max-classes-per-file */
import { AxiosError, isAxiosError } from 'axios';

import type { APIErrorCode, ErrorCode, RuntimeErrorCode } from '../constants/errors';
import { API_ERROR_CODE_KIT, ERROR_MESSAGE_KIT, IGNORE_KEY } from '../constants/errors';

type ManageableStandard = 'config' | 'request' | 'response';

type StandardInfo<E extends AxiosError> = Pick<E, ManageableStandard>;

type ManageableAxiosError<E extends AxiosError> = {
  [key in keyof StandardInfo<E>]-?: StandardInfo<E>[key];
} & Omit<E, ManageableStandard>;

type WithAPIErrorCode<T> = T & {
  code: APIErrorCode;
};

type ErrorInfo<T extends ErrorCode> = {
  code: T;
};

type ZipgoErrorOptions<T extends ErrorCode> = {
  cause: { code: T; value?: unknown };
};

class ZipgoError<Code extends ErrorCode> extends Error {
  cause: ZipgoErrorOptions<Code>['cause'];

  [IGNORE_KEY]: boolean;

  constructor(info: ErrorInfo<Code>, value?: unknown) {
    const [message, options] = createErrorParams(info, value);

    super(message, options);

    this.name = info.code;

    this.message = message;

    this.cause = options.cause;

    this.ignore = false;
  }

  static convertToError(error: unknown) {
    if (!isAxiosError(error) || !APIError.canManage(error)) {
      return new UnexpectedError(error);
    }

    return new APIError(error);
  }
}

class RuntimeError<Code extends RuntimeErrorCode> extends ZipgoError<Code> {
  constructor(info: ErrorInfo<Code>, value?: unknown) {
    super(info, JSON.stringify(value));

    this.ignore = true;
  }
}

class UnexpectedError extends ZipgoError<'UNEXPECTED_ERROR'> {
  constructor(value?: unknown) {
    super({ code: 'UNEXPECTED_ERROR' }, value);

    this.ignore = true;
  }
}

class APIError<T, D> extends ZipgoError<APIErrorCode> {
  status;

  constructor(error: ManageableAxiosError<AxiosError<WithAPIErrorCode<T>, D>>) {
    /** @description 서버의 코드 미제공 방지 */
    const code = error.response.data.code || API_ERROR_CODE_KIT.API_ERROR_CODE_MISSING;

    super({ code });

    this.status = error.response.status;
  }

  /**
   * @description `No Internet` 과 `Server not reachable' 구분이 되지 않지만 online event로 핸들링한다
   */
  static canManage<T, D>(error: AxiosError<T, D>): error is ManageableAxiosError<typeof error> {
    return error.config && error.request && error.response;
  }
}

const createErrorParams = <Code extends ErrorCode>(
  info: ErrorInfo<Code>,
  value?: unknown,
): [string, ZipgoErrorOptions<Code>] => {
  /** @description 서버의 잘못된 코드 제공 방지 */
  const message = ERROR_MESSAGE_KIT[info.code] || ERROR_MESSAGE_KIT.UNEXPECTED_ERROR;
  const options = { cause: { code: info.code, value } };

  return [message, options];
};

export { APIError, RuntimeError, UnexpectedError, ZipgoError };

export type { ManageableAxiosError };
