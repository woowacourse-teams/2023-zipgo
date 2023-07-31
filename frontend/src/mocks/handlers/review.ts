import { rest } from 'msw';

import { BASE_URL } from '@/apis';

import reviewFixture from '../fixtures/review';

const reviewHandlers = [
  rest.get(`${BASE_URL}/pet-foods/:petFoodId/reviews`, (req, res, ctx) => {
    const reviews = reviewFixture.getReviews();

    return res(ctx.status(200), ctx.json(reviews));
  }),
];

export default reviewHandlers;
