import { PATH } from '@/router/routes';

export const PET_AGE_MIN = 0;
export const PET_AGE_MAX = 20;
export const PET_AGE_ADULT = 6;

export const MIXED_BREED = '믹스견';

export const PET_SIZES = ['소형견', '중형견', '대형견'] as const;
export const GENDERS = ['남', '여'] as const;
export const MALE = '남';
export const FEMALE = '여';
export const AGE_GROUP = ['퍼피', '어덜트', '시니어'] as const;

export const PET_PROFILE_ADDITION_STEP = {
  NAME: 1,
  AGE: 2,
  BREED: 3,
  PET_SIZE: 4,
  GENDER: 5,
  WEIGHT: 6,
  IMAGE_FILE: 7,
} as const;

export const STEP_PATH: Record<number, string> = {
  [PET_PROFILE_ADDITION_STEP.NAME]: PATH.PET_PROFILE_NAME_ADDITION,
  [PET_PROFILE_ADDITION_STEP.AGE]: PATH.PET_PROFILE_AGE_ADDITION,
  [PET_PROFILE_ADDITION_STEP.BREED]: PATH.PET_PROFILE_BREED_ADDITION,
  [PET_PROFILE_ADDITION_STEP.PET_SIZE]: PATH.PET_PROFILE_PET_SIZE_ADDITION,
  [PET_PROFILE_ADDITION_STEP.GENDER]: PATH.PET_PROFILE_GENDER_ADDITION,
  [PET_PROFILE_ADDITION_STEP.WEIGHT]: PATH.PET_PROFILE_WEIGHT_ADDITION,
  [PET_PROFILE_ADDITION_STEP.IMAGE_FILE]: PATH.PET_PROFILE_IMAGE_FILE_ADDITION,
} as const;

export const PET_ERROR_MESSAGE = {
  INVALID_NAME: '아이의 이름은 1~10글자 사이의 한글, 영어, 숫자만 입력 가능합니다.',
  INVALID_WEIGHT: '몸무게는 0kg초과, 100kg이하 소수점 첫째짜리까지 입력이 가능합니다.',
} as const;
