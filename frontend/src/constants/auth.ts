import { PATH } from '@/router/routes';

const { KAKAO_REST_API_KEY, HOMEPAGE } = process.env;

export const KAKAO_REDIRECT_URI = `${HOMEPAGE}${PATH.LOGIN}`;

export const KAKAO_HREF = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_REST_API_KEY}&redirect_uri=${encodeURI(
  KAKAO_REDIRECT_URI,
)}&response_type=code`;
