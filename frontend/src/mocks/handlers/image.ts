import { rest } from 'msw';

import { BASE_URL } from '@/apis';

const imageHandlers = [
  rest.post(`${BASE_URL}/images`, async (req, res, ctx) =>
    res(
      ctx.status(201),
      ctx.json({
        imageUrl: 'https://avatars.githubusercontent.com/u/24777828?v=4',
      }),
    ),
  ),
];

export default imageHandlers;
