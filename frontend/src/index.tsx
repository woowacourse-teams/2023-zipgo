import React from 'react';
import ReactDOM from 'react-dom/client';

import { startWorker } from './mocks/worker';
import Router from './router/Router';

startWorker();

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

const strictMode = process.env.STRICT_MODE === 'on';

/** @todo 개발 서버 config를 webpack.prod.js로 적용 */
root.render(
  strictMode ? (
    <React.StrictMode>
      <Router />
    </React.StrictMode>
  ) : (
    <Router />
  ),
);
