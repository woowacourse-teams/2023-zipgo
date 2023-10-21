import { useQuery } from '@tanstack/react-query';
import { ComponentPropsWithoutRef, memo } from 'react';

import { useIntersectionObserver } from '@/hooks/@common/useIntersectionObserver';

import LazyImage from '../LazyImage/LazyImage';

interface SuspendedImgProps extends ComponentPropsWithoutRef<'img'> {
  staleTime?: number;
  cacheTime?: number;
  lazy?: boolean;
}

const SuspendedImg = (props: SuspendedImgProps) => {
  const { src, cacheTime, staleTime, lazy, ...restProps } = props;

  const img = new Image();

  const { targetRef, isIntersected } = useIntersectionObserver<HTMLImageElement>({
    observerOptions: { threshold: 0.1 },
  });

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
    enabled: lazy ? isIntersected : true,
  });

  if (lazy) {
    return <LazyImage src={src} ref={targetRef} {...restProps} />;
  }
  // eslint-disable-next-line jsx-a11y/alt-text
  return <img src={src} {...restProps} />;
};

export default memo(SuspendedImg);
