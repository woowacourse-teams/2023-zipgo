import { PathParams, QueryStringProperty } from '@/types/common/routes';
import type { CamelToSnake } from '@/types/common/utility';

const STATIC: Record<Uppercase<CamelToSnake<keyof typeof generateStaticPath>>, string> = {
  HOME: '/',
  LOGIN: '/login',
  PET_PROFILE_ADDITION: '/pets/write/',
  PET_PROFILE_NAME_ADDITION: '/pets/write/name',
  PET_PROFILE_AGE_ADDITION: '/pets/write/age',
  PET_PROFILE_BREED_ADDITION: '/pets/write/breed',
  PET_PROFILE_PET_SIZE_ADDITION: '/pets/write/petSize',
  PET_PROFILE_GENDER_ADDITION: '/pets/write/gender',
  PET_PROFILE_WEIGHT_ADDITION: '/pets/write/weight',
  PET_PROFILE_IMAGE_FILE_ADDITION: '/pets/write/imageFile',
};

const DYNAMIC: Record<Uppercase<CamelToSnake<keyof typeof generateDynamicPath>>, string> = {
  FOOD_DETAIL: '/pet-food/:petFoodId',
  REVIEW_STAR_RATING: '/pet-food/:petFoodId/reviews/write',
  REVIEW_ADDITION: '/pet-food/:petFoodId/reviews/write/detail',
};

export const PATH = {
  ...STATIC,
  ...DYNAMIC,
} as const;

export const generateStaticPath = {
  home: () => STATIC.HOME,
  login: () => STATIC.LOGIN,
  petProfileAddition: () => STATIC.PET_PROFILE_ADDITION,
  petProfileNameAddition: () => STATIC.PET_PROFILE_NAME_ADDITION,
  petProfileAgeAddition: () => STATIC.PET_PROFILE_AGE_ADDITION,
  petProfileBreedAddition: () => STATIC.PET_PROFILE_BREED_ADDITION,
  petProfilePetSizeAddition: () => STATIC.PET_PROFILE_PET_SIZE_ADDITION,
  petProfileGenderAddition: () => STATIC.PET_PROFILE_GENDER_ADDITION,
  petProfileWeightAddition: () => STATIC.PET_PROFILE_WEIGHT_ADDITION,
  petProfileImageFileAddition: () => STATIC.PET_PROFILE_IMAGE_FILE_ADDITION,
};

export const generateDynamicPath = {
  foodDetail: ({ petFoodId }: PathParams<'petFoodId'>) => `/pet-food/${petFoodId}`,
  reviewStarRating: ({ petFoodId }: PathParams<'petFoodId'>) =>
    `/pet-food/${petFoodId}/reviews/write`,
  reviewAddition: ({ petFoodId }: PathParams<'petFoodId'>) =>
    `/pet-food/${petFoodId}/reviews/write/detail`,
};

export const routerPath = {
  ...generateDynamicPath,
  ...generateStaticPath,
  back: -1,
} as const;

export const generateQueryString = (keyValue: QueryStringProperty) =>
  Object.entries(keyValue)
    .reduce((queryString, [key, value]) => {
      const queryValue = Array.isArray(value) ? value.join() : value;

      if (queryValue) {
        return `${queryString}${key}=${queryValue}&`;
      }

      return queryString;
    }, '?')
    .slice(0, -1);
