import { setupWorker } from 'msw';

import handlers from './handlers';

export const worker = setupWorker(...handlers);

export const startWorker = () => {
  if (process.env.MSW === 'on') {
    worker.start({ serviceWorker: { url: '/mockServiceWorker.js' } });
  }
};
