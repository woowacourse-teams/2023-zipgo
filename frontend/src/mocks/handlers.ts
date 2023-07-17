import { rest } from 'msw';

const todos = ['먹기', '자기', '놀기'];

const handlers = [rest.get('/todos', (req, res, ctx) => res(ctx.status(200), ctx.json(todos)))];

export default handlers;
