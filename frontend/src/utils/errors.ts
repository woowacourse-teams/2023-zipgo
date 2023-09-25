/* eslint-disable max-classes-per-file */
import type { ErrorCode, RuntimeErrorCode } from '../constants/errors';
import { ERROR_MESSAGE_KIT } from '../constants/errors';

type ErrorInfo<T extends ErrorCode = 'UNEXPECTED_ERROR'> = {
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

    if (error) {
      this.message = error.message;
    }

    this.ignore = true;
  }
}
