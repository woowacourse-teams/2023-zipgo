import { rest } from 'msw';

import { BASE_URL } from '@/apis';

import authFixture from '../fixtures/auth';

const authHandlers = [
  rest.post(`${BASE_URL}/login/zipgo`, (req, res, ctx) => {
    const { accessToken } = authFixture.loginZipgoAuth();

    return res(ctx.status(200), ctx.json({ accessToken }));
  }),
];

export default authHandlers;
