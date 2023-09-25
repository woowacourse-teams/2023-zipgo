/* eslint-disable max-classes-per-file */
import { AxiosError } from 'axios';

import type { APIErrorCode, ErrorCode, RuntimeErrorCode } from '../constants/errors';
import { API_ERROR_CODE_KIT, ERROR_MESSAGE_KIT } from '../constants/errors';

type ManageableStandard = 'config' | 'request' | 'response';

type StandardInfo<E extends AxiosError> = Pick<E, ManageableStandard>;

type ManageableAxiosError<E extends AxiosError> = {
  [key in keyof StandardInfo<E>]-?: StandardInfo<E>[key];
} & Omit<E, ManageableStandard>;

type ErrorInfo<T extends ErrorCode> = {
  code: T;
};

type CustomErrorOptions<T extends ErrorCode> = {
  cause: { code: T; value?: unknown };
};

const createErrorParams = <Code extends ErrorCode>(
  info: ErrorInfo<Code>,
  value?: unknown,
): [string, CustomErrorOptions<Code>] => {
  const message = ERROR_MESSAGE_KIT[info.code];
  const options = { cause: { code: info.code, value } };

  return [message, options];
};

class CustomError<Code extends ErrorCode> extends Error {
  cause: CustomErrorOptions<Code>['cause'];

  ignore: boolean;

  constructor(info: ErrorInfo<Code>, value?: unknown) {
    const [message, options] = createErrorParams(info, value);

    super(message, options);

    this.name = info.code;

    this.message = message;

    this.cause = options.cause;

    this.ignore = false;
  }
}

export class RuntimeError<Code extends RuntimeErrorCode> extends CustomError<Code> {
  constructor(info: ErrorInfo<Code>, value?: unknown) {
    super(info, JSON.stringify(value));

    this.ignore = true;
  }
}

export class UnexpectedError extends CustomError<'UNEXPECTED_ERROR'> {
  constructor(error?: Error) {
    super({ code: 'UNEXPECTED_ERROR' }, error);

    this.ignore = true;
  }
}
export class APIError<Code extends ErrorCode> extends CustomError<Code> {
  // constructor(value?: unknown) {
  //   super({ code: 'RUNTIME_ERROR' }, JSON.stringify(value));
  //   this.name = ERROR_CODE_KIT.RUNTIME_ERROR;
  // }
}

export type { ManageableAxiosError };
