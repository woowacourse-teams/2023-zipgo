import { rest } from 'msw';

import { BASE_URL } from '@/apis';

import petProfileFixture from '../fixtures/petProfile';

const petProfileHandlers = [
  rest.get(`${BASE_URL}/pets/breeds`, (req, res, ctx) => {
    const breeds = petProfileFixture.getBreeds();

    return res(ctx.status(200), ctx.json(breeds));
  }),
];

export default petProfileHandlers;
