import { useState } from 'react';

const useErrorBoundary = <E extends Error>() => {
  const [error, setError] = useState<E | null>(null);

  if (error != null) {
    throw error;
  }

  return { setError };
};

export default useErrorBoundary;
