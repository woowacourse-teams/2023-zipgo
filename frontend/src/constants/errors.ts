export type ErrorCode = keyof typeof ERROR_CODE_KIT;
export type RuntimeErrorCode = keyof typeof RUNTIME_ERROR_CODE_KIT;
export type ErrorCodeKit = typeof ERROR_CODE_KIT;
export type ErrorMessageKit = typeof ERROR_MESSAGE_KIT;
export type RuntimeErrorMessageKit = typeof RUNTIME_ERROR_MESSAGE_KIT;

const UNEXPECTED_ERROR = '알 수 없는 오류가 발생했습니다.';
const WRONG_URL_FORMAT = '잘못된 경로 형식입니다.';
const WRONG_QUERY_STRING = '잘못된 쿼리 스트링입니다.';

export const RUNTIME_ERROR_CODE_KIT = {
  WRONG_URL_FORMAT: 'WRONG_URL_FORMAT',
  WRONG_QUERY_STRING: 'WRONG_QUERY_STRING',
} as const;

export const ERROR_CODE_KIT: Record<keyof ErrorMessageKit, keyof ErrorMessageKit> = {
  UNEXPECTED_ERROR: 'UNEXPECTED_ERROR',
  ...RUNTIME_ERROR_CODE_KIT,
};

export const RUNTIME_ERROR_MESSAGE_KIT = {
  WRONG_URL_FORMAT,
  WRONG_QUERY_STRING,
} as const;

export const ERROR_MESSAGE_KIT = {
  UNEXPECTED_ERROR,
  ...RUNTIME_ERROR_MESSAGE_KIT,
} as const;
