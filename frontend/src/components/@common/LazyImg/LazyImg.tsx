import { ComponentPropsWithoutRef, ForwardedRef, forwardRef, memo, useCallback } from 'react';

import { useIntersectionObserver } from '@/hooks/@common/useIntersectionObserver';

const LazyImg = forwardRef(
  (props: ComponentPropsWithoutRef<'img'>, ref: ForwardedRef<HTMLImageElement>) => {
    const { src, ...restProps } = props;

    const { targetRef, isIntersected } = useIntersectionObserver<HTMLImageElement>({
      observerOptions: { threshold: 0.1 },
    });

    const callbackRef = useCallback((instance: HTMLImageElement | null) => {
      targetRef.current = instance;

      if (!ref) return;

      // eslint-disable-next-line no-param-reassign
      typeof ref === 'function' ? ref(instance) : (ref.current = instance);
    }, []);

    if (isIntersected && targetRef.current && !targetRef.current.src && src) {
      targetRef.current.src = src;
    }

    // eslint-disable-next-line jsx-a11y/alt-text
    return <img {...restProps} loading="lazy" ref={callbackRef} />;
  },
);

LazyImg.displayName = 'LazyImg';

export default memo(LazyImg);
