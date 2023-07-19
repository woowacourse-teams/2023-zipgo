import { rest } from 'msw';

import { BASE_URL } from '@/apis';

import foodFixture from '../fixtures/food';

const foodHandlers = [
  rest.get(`${BASE_URL}/api/v1/foodList`, (req, res, ctx) => {
    const foodList = foodFixture.getFoodList();

    return res(ctx.status(200), ctx.json(foodList));
  }),
];

export default foodHandlers;
