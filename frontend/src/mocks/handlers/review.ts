import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import { Review } from '@/types/review/client';

import reviewFixture from '../fixtures/review';

const reviewHandlers = [
  rest.get(`${BASE_URL}/reviews/metadata`, async (req, res, ctx) => {
    const metaData = reviewFixture.getReviewsMeta();

    return res(ctx.status(200), ctx.json(metaData));
  }),

  rest.get(`${BASE_URL}/reviews/summary?petFoodId`, (req, res, ctx) => {
    const reviewSummary = reviewFixture.getReviewSummary();

    return res(ctx.delay(2000), ctx.status(200), ctx.json(reviewSummary));
  }),

  rest.get(`${BASE_URL}/reviews/:reviewId`, (req, res, ctx) => {
    const { reviews } = reviewFixture.getReviews();
    const review = reviews.find(review => review.id === Number(req.params.reviewId)) as Review;

    return res(ctx.json(review));
  }),

  rest.post(`${BASE_URL}/reviews/:reviewId/helpful-reactions`, (req, res, ctx) =>
    res(ctx.delay(2000), ctx.status(200)),
  ),

  rest.delete(`${BASE_URL}/reviews/:reviewId/helpful-reactions`, (req, res, ctx) =>
    res(ctx.status(200)),
  ),

  rest.get(`${BASE_URL}/reviews`, (req, res, ctx) => {
    const reviews = reviewFixture.getReviews();

    return res(ctx.status(200), ctx.json(reviews));
  }),

  rest.post(`${BASE_URL}/reviews`, async (req, res, ctx) => {
    const { reviews } = reviewFixture.getReviews();
    const newReviews = [
      ...reviews,
      {
        id: 4,
        reviewerName: '베베',
        rating: 5,
        date: '2023-08-01',
        comment: '저희집 갈비가 잘 먹어요',
        tastePreference: '잘 먹는 편이에요',
        stoolCondition: '설사를 해요',
        adverseReactions: ['없어요'],
      },
    ];

    return res(ctx.status(201), ctx.set('Location', `/reviews/${newReviews.at(-1)?.id}`));
  }),

  rest.put(`${BASE_URL}/reviews/:reviewId`, async (req, res, ctx) => res(ctx.status(204))),

  rest.delete(`${BASE_URL}/reviews/:reviewId`, (req, res, ctx) => res(ctx.status(204))),
];

export default reviewHandlers;
