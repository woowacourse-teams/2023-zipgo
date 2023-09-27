export type ErrorCode = keyof typeof ERROR_CODE_KIT;
export type RuntimeErrorCode = keyof typeof RUNTIME_ERROR_CODE_KIT;
export type APIErrorCode = keyof typeof API_ERROR_CODE_KIT;
export type ErrorCodeKit = typeof ERROR_CODE_KIT;
export type ErrorMessageKit = typeof ERROR_MESSAGE_KIT;
export type RuntimeErrorMessageKit = typeof RUNTIME_ERROR_MESSAGE_KIT;
export type APIErrorMessageKit = typeof API_ERROR_MESSAGE_KIT;

export const DEFAULT_STATUS = 404;

const UNEXPECTED_ERROR = '서비스에 문제가 발생했어요';
const NOT_FOUND = '존재하지 않는 페이지예요';
const WRONG_URL_FORMAT = NOT_FOUND;
const WRONG_QUERY_STRING = NOT_FOUND;

const API_ERROR_CODE_MISSING = UNEXPECTED_ERROR;

export const RUNTIME_ERROR_CODE_KIT = {
  WRONG_URL_FORMAT: 'WRONG_URL_FORMAT',
  WRONG_QUERY_STRING: 'WRONG_QUERY_STRING',
} as const;

export const API_ERROR_CODE_KIT = {
  API_ERROR_CODE_MISSING: 'API_ERROR_CODE_MISSING',
} as const;

export const ERROR_CODE_KIT: Record<keyof ErrorMessageKit, keyof ErrorMessageKit> = {
  UNEXPECTED_ERROR: 'UNEXPECTED_ERROR',
  NOT_FOUND: 'NOT_FOUND',
  ...RUNTIME_ERROR_CODE_KIT,
  ...API_ERROR_CODE_KIT,
};

export const RUNTIME_ERROR_MESSAGE_KIT = {
  WRONG_URL_FORMAT,
  WRONG_QUERY_STRING,
} as const;

export const API_ERROR_MESSAGE_KIT = {
  API_ERROR_CODE_MISSING,
} as const;

export const ERROR_MESSAGE_KIT = {
  UNEXPECTED_ERROR,
  NOT_FOUND,
  ...RUNTIME_ERROR_MESSAGE_KIT,
  ...API_ERROR_MESSAGE_KIT,
} as const;
