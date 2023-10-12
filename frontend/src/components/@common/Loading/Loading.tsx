import { PropsWithChildren, Suspense } from 'react';

import LoadingSpinner from '../LoadingSpinner/LoadingSpinner';

const Loading = ({ children }: PropsWithChildren) => (
  <Suspense fallback={<LoadingSpinner />}>{children}</Suspense>
);

export default Loading;
