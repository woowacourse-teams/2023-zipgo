import { MutableRefObject, useCallback, useEffect, useMemo, useRef, useState } from 'react';

interface UseIntersectionObserverOptions<T extends HTMLElement> {
  ref?: MutableRefObject<T | null>;
  observerOptions?: IntersectionObserverInit;
}

export const useIntersectionObserver = <T extends HTMLElement>(
  options?: UseIntersectionObserverOptions<T>,
) => {
  const localRef: MutableRefObject<T | null> = useRef<T>(null);

  const observerRef = useRef<IntersectionObserver | null>(null);

  const [isIntersected, setIsIntersected] = useState<boolean>(false);

  const targetRef = options?.ref || localRef;

  const getObserver = useCallback(() => {
    if (!observerRef.current) {
      observerRef.current = new IntersectionObserver(entries => {
        setIsIntersected(entries.some(entry => entry.isIntersecting));
      }, options?.observerOptions);
    }

    return observerRef.current;
  }, []);

  useEffect(() => {
    if (targetRef.current) getObserver().observe(targetRef.current);

    return () => {
      getObserver().disconnect();
    };
  }, [targetRef, getObserver]);

  return { targetRef, isIntersected };
};
