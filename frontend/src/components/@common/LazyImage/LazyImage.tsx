import { ComponentPropsWithoutRef, useEffect, useRef } from 'react';

import { useIntersectionObserver } from '@/hooks/@common/useIntersectionObserver';

interface LazyImageProps extends ComponentPropsWithoutRef<'img'> {
  src: string;
}

const LazyImage = (props: LazyImageProps) => {
  const { src, ...restProps } = props;

  const { targetRef, isIntersected } = useIntersectionObserver<HTMLImageElement>({
    observerOptions: { threshold: 0.1 },
  });

  useEffect(() => {
    if (!targetRef.current) return;

    if ('loading' in HTMLImageElement.prototype || isIntersected) {
      targetRef.current.src = String(targetRef.current.dataset.src);
    }
  }, [isIntersected]);

  return <img alt="" {...restProps} loading="lazy" ref={targetRef} data-src={src} />;
};

export default LazyImage;
