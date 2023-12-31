/** @description kakao REST API error response - kauth.kakao.com */

import { User } from './client';

/** @see https://developers.kakao.com/docs/latest/ko/reference/rest-api-reference#response-format */
export interface KakaoAuthError {
  error: string;
  error_description: string;
}

/** @description kakao REST API error response - kapi.kakao.com */
/** @see https://developers.kakao.com/docs/latest/ko/reference/rest-api-reference#response-format */
export interface KakaoApiError {
  msg: string;
  code: number;
}

export interface LoginZipgoAuthReq {
  code: string;
}

export interface LoginZipgoAuthRes {
  accessToken: string;
  refreshToken: string;
  authResponse: User;
}

export interface RefreshZipgoAuthRes {
  accessToken: string;
}

export interface LoginKakaoAuthReq {
  code: string;
}

export interface LoginKakaoAuthRes {}

export interface LogoutKakaoAuthRes {
  id: number;
}

export interface LogoutKaKaoAuthError extends KakaoApiError {}

export interface AuthenticateUserRes {}
