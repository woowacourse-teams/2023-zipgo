import { PATH } from '@/router/routes';

const HOMEPAGE = process.env.HOMEPAGE;
const KAKAO_REST_API_KEY = process.env.KAKAO_REST_API_KEY;

export const KAKAO_REDIRECT_URI = `${HOMEPAGE}${PATH.LOGIN}`;

export const KAKAO_HREF = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_REST_API_KEY}&redirect_uri=${encodeURI(
  KAKAO_REDIRECT_URI,
)}&response_type=code`;
