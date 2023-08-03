import { rest } from 'msw';

import { BASE_URL } from '@/apis';

import foodFixture from '../fixtures/food';

const foodHandlers = [
  rest.get(`${BASE_URL}/pet-foods`, (req, res, ctx) => {
    const foodList = foodFixture.getFoodList();

    return res(ctx.status(200), ctx.json(foodList));
  }),

  rest.get(`${BASE_URL}/pet-foods/:petFoodId`, (req, res, ctx) => {
    const { petFoodId } = req.params;

    const foodDetail = foodFixture.getFoodDetail();

    return res(ctx.status(200), ctx.json(foodDetail));
  }),

  rest.get(`${BASE_URL}/api/v1/foodListFilterMeta`, (req, res, ctx) => {
    const foodListFilterMeta = foodFixture.getFoodListFilterMeta();

    return res(ctx.status(200), ctx.json(foodListFilterMeta));
  }),
];

export default foodHandlers;
