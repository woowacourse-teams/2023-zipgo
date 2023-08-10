import { PathParams, QueryStringProperty } from '@/types/common/routes';
import type { CamelToSnake } from '@/types/common/utility';

const STATIC: Record<Uppercase<CamelToSnake<keyof typeof generateStaticPath>>, string> = {
  HOME: '/',
  LOGIN: '/login',
};

const DYNAMIC: Record<Uppercase<CamelToSnake<keyof typeof generateDynamicPath>>, string> = {
  FOOD_DETAIL: '/pet-food/:petFoodId',
  REVIEW_STAR_RATING: '/pet-food/:petFoodId/reviews/write',
  REVIEW_ADDITION: '/pet-food/:petFoodId/reviews/write/:rating/detail/:isEditMode',
};

export const PATH = {
  ...STATIC,
  ...DYNAMIC,
} as const;

export const generateStaticPath = {
  home: () => STATIC.HOME,
  login: () => STATIC.LOGIN,
};

export const generateDynamicPath = {
  foodDetail: ({ petFoodId }: PathParams<'petFoodId'>) => `/pet-food/:${petFoodId}`,
  reviewStarRating: ({ petFoodId }: PathParams<'petFoodId'>) =>
    `/pet-food/${petFoodId}}/reviews/write`,
  reviewAddition: ({
    petFoodId,
    rating,
    isEditMode,
  }: PathParams<'petFoodId' | 'rating'> & { isEditMode: boolean }) =>
    `/pet-food/${petFoodId}/reviews/write/${rating}/detail/${isEditMode}`,
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
