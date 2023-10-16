import { useQuery } from '@tanstack/react-query';
import { ComponentPropsWithoutRef, ComponentPropsWithRef, useEffect } from 'react';

import { useIntersectionObserver } from '@/hooks/@common/useIntersectionObserver';

interface SuspendedImgProps extends ComponentPropsWithoutRef<'img'> {
  staleTime?: number;
  cacheTime?: number;
  enabled?: boolean;
  lazy?: boolean;
}

// eslint-disable-next-line react/display-name
const SuspendedImg = (props: SuspendedImgProps) => {
  const { src, cacheTime, staleTime, enabled, lazy, ...restProps } = props;

  const img = new Image();

  const { targetRef, isIntersected } = useIntersectionObserver<HTMLImageElement>({
    observerOptions: { threshold: 0.1 },
  });

  const lazyOptions: ComponentPropsWithRef<'img'> & { 'data-src'?: string } = {
    loading: 'lazy',
    ref: targetRef,
    'data-src': src,
  };

  useQuery({
    queryKey: [src],
    queryFn: () =>
      new Promise(resolve => {
        img.onload = resolve;
        img.onerror = resolve;

        img.src = src!;
      }),
    ...(staleTime == null ? {} : { staleTime }),
    ...(cacheTime == null ? {} : { cacheTime }),
    enabled: enabled && Boolean(src),
  });

  useEffect(() => {
    if (!targetRef.current) return;

    if ('loading' in HTMLImageElement.prototype || isIntersected) {
      targetRef.current.src = String(targetRef.current.dataset.src);
    }
  }, [isIntersected]);

  // eslint-disable-next-line jsx-a11y/alt-text
  return <img {...restProps} {...(lazy ? lazyOptions : { src })} />;
};

export default SuspendedImg;
