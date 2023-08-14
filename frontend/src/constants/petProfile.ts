import { PATH } from '@/router/routes';

export const BREEDS_SIZES = ['소형견', '중형견', '대형견'] as const;
export const GENDERS = ['남', '여'] as const;
export const MALE = '남';
export const FEMALE = '여';

export const PET_PROFILE_ADDITION_STEP = {
  NAME: 1,
  AGE: 2,
  BREEDS: 3,
  BREEDS_SIZE: 4,
  GENDER: 5,
  WEIGHT: 6,
  IMAGE_FILE: 7,
} as const;

export const STEP_PATH: Record<number, string> = {
  [PET_PROFILE_ADDITION_STEP.NAME]: PATH.PET_PROFILE_NAME_ADDITION,
  [PET_PROFILE_ADDITION_STEP.AGE]: PATH.PET_PROFILE_AGE_ADDITION,
  [PET_PROFILE_ADDITION_STEP.BREEDS]: PATH.PET_PROFILE_BREEDS_ADDITION,
  [PET_PROFILE_ADDITION_STEP.BREEDS_SIZE]: PATH.PET_PROFILE_BREEDS_SIZE_ADDITION,
  [PET_PROFILE_ADDITION_STEP.GENDER]: PATH.PET_PROFILE_GENDER_ADDITION,
  [PET_PROFILE_ADDITION_STEP.WEIGHT]: PATH.PET_PROFILE_WEIGHT_ADDITION,
  [PET_PROFILE_ADDITION_STEP.IMAGE_FILE]: PATH.PET_PROFILE_IMAGE_FILE_ADDITION,
} as const;
