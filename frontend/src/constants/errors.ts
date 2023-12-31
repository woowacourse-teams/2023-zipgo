type ErrorCode = keyof typeof ERROR_CODE_KIT;
type RuntimeErrorCode = keyof typeof RUNTIME_ERROR_CODE_KIT;
type APIErrorCode = keyof typeof API_ERROR_CODE_KIT;
type ErrorMessageKit = typeof ERROR_MESSAGE_KIT;

const DEFAULT_STATUS = 404;

const IGNORE_KEY = 'ignore';

const OFFLINE = '네트워크 상태를 확인해 주세요!';
const ONLINE = '네트워크가 연결되었습니다!';

const UNEXPECTED_ERROR = '서비스에 문제가 발생했어요';
const NOT_FOUND = '존재하지 않는 페이지예요';
const KAKAO_LOGIN_ERROR = '카카오 서버에 문제가 발생했어요\n잠시 뒤 다시 시도해 주세요';
const WRONG_URL_FORMAT = NOT_FOUND;
const WRONG_QUERY_STRING = NOT_FOUND;

const API_ERROR_CODE_MISSING = UNEXPECTED_ERROR;

const RUNTIME_ERROR_CODE_KIT = {
  WRONG_URL_FORMAT: 'WRONG_URL_FORMAT',
  WRONG_QUERY_STRING: 'WRONG_QUERY_STRING',
  KAKAO_LOGIN_ERROR: 'KAKAO_LOGIN_ERROR',
} as const;

const API_ERROR_CODE_KIT = {
  API_ERROR_CODE_MISSING: 'API_ERROR_CODE_MISSING',
} as const;

const ERROR_CODE_KIT: Record<keyof ErrorMessageKit, keyof ErrorMessageKit> = {
  UNEXPECTED_ERROR: 'UNEXPECTED_ERROR',
  NOT_FOUND: 'NOT_FOUND',
  OFFLINE: 'OFFLINE',
  ONLINE: 'ONLINE',
  ...RUNTIME_ERROR_CODE_KIT,
  ...API_ERROR_CODE_KIT,
};

const RUNTIME_ERROR_MESSAGE_KIT = {
  WRONG_URL_FORMAT,
  WRONG_QUERY_STRING,
  KAKAO_LOGIN_ERROR,
} as const;

const API_ERROR_MESSAGE_KIT = {
  API_ERROR_CODE_MISSING,
} as const;

const ERROR_MESSAGE_KIT = {
  UNEXPECTED_ERROR,
  NOT_FOUND,
  OFFLINE,
  ONLINE,
  ...RUNTIME_ERROR_MESSAGE_KIT,
  ...API_ERROR_MESSAGE_KIT,
} as const;

export type { APIErrorCode, ErrorCode, RuntimeErrorCode };

export { API_ERROR_CODE_KIT, DEFAULT_STATUS, ERROR_CODE_KIT, ERROR_MESSAGE_KIT, IGNORE_KEY };
