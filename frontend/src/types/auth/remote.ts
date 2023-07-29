export interface KAuthError {
  error: string;
  error_description: string;
}

export interface KApiError {
  msg: string;
  code: number;
}

export interface LoginZipgoAuthReq {
  code: string;
}

export interface LoginZipgoAuthRes {
  accessToken: string;
}

export interface LoginKakaoAuthReq {
  code: string;
}

export interface LoginKakaoAuthRes {}

export interface LogoutKakaoAuthRes {
  id: number;
}

export interface LogoutKaKaoAuthError extends KApiError {}
