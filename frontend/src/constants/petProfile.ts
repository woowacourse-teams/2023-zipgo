import { Options } from 'browser-image-compression';

export const PET_AGE_MIN = 0;
export const PET_AGE_MAX = 20;
export const PET_AGE_ADULT = 6;

export const MIXED_BREED = '믹스견';

export const PET_SIZES = ['소형견', '중형견', '대형견'] as const;
export const GENDERS = ['남', '여'] as const;
export const MALE = '남';
export const FEMALE = '여';
export const AGE_GROUP = ['퍼피', '어덜트', '시니어'] as const;
export const AGE_GROUP_ID = {
  PUPPY: 1,
  ADULT: 2,
  SENIOR: 3,
} as const;

export const PET_PROFILE_ADDITION_STEP = [
  'NAME',
  'AGE',
  'BREED',
  'PET_SIZE',
  'GENDER',
  'WEIGHT',
  'IMAGE_FILE',
] as const;

export const PET_ERROR_MESSAGE = {
  INVALID_NAME: '아이의 이름은 1~10글자 사이의 한글, 영어, 숫자만 입력 가능합니다.',
  INVALID_WEIGHT: '몸무게는 0kg초과, 100kg이하 소수점 첫째짜리까지 입력이 가능합니다.',
} as const;

export const PET_PROFILE_IMAGE_MAX_SIZE = 1000;
export const PET_PROFILE_IMAGE_COMPRESSION_OPTION: Options = {
  maxSizeMB: 1,
  maxWidthOrHeight: PET_PROFILE_IMAGE_MAX_SIZE,
  useWebWorker: true,
};
