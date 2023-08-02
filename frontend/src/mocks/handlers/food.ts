import { rest } from 'msw';

import { BASE_URL } from '@/apis';

import foodFixture from '../fixtures/food';

const foodHandlers = [
  rest.post(`${BASE_URL}/pet-foods`, (req, res, ctx) => {
    const foodList = foodFixture.getFoodList();

    return res(ctx.status(200), ctx.json(foodList));
  }),
  rest.get(`${BASE_URL}/pet-foods/:petFoodId`, (req, res, ctx) => {
    const { petFoodId } = req.params;

    const foodDetail = foodFixture.getFoodDetail();

    return res(ctx.status(200), ctx.json(foodDetail));
  }),
];

export default foodHandlers;
