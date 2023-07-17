import { setupWorker } from 'msw';

import handlers from './handlers';

export const worker = setupWorker(...handlers);

export const startWorker = () => {
  if (process.env.NODE_ENV === 'development') {
    worker.start({
      serviceWorker: {
        url: './mockServiceWorker.js',
      },
    });
  }
};
