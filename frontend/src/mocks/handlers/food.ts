import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import { GetFoodListRes } from '@/types/food/remote';

import foodFixture from '../fixtures/food';

const foodHandlers = [
  rest.get(`${BASE_URL}/pet-foods`, (req, res, ctx) => {
    const lastPetFoodId = Number(req.url.searchParams.get('lastPetFoodId')) ?? 0;
    const size = Number(req.url.searchParams.get('size'));
    const foodList = foodFixture.getFoodList();

    const petFoods = foodList.petFoods.slice(lastPetFoodId, lastPetFoodId + size);
    const responseData = { ...foodList, petFoods } as GetFoodListRes;

    return res(ctx.status(200), ctx.json(responseData));
  }),

  rest.get(`${BASE_URL}/pet-foods/:petFoodId`, (req, res, ctx) => {
    const foodDetail = foodFixture.getFoodDetail();

    return res(ctx.status(200), ctx.json(foodDetail));
  }),

  rest.get(`${BASE_URL}/pet-foods/filters`, (req, res, ctx) => {
    const foodListFilterMeta = foodFixture.getFoodListFilterMeta();

    return res(ctx.status(200), ctx.json(foodListFilterMeta));
  }),
];

export default foodHandlers;
