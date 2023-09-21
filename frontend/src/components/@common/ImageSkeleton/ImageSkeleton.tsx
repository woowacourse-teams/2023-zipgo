import { PropsWithChildren, ReactNode, useEffect } from 'react';

import useBoolean from '@/hooks/@common/useBoolean';

interface ImageSkeletonProps {
  src: string;
  fallback: ReactNode;
  timeout?: number;
}

const IMG_LOAD_TIMEOUT = 5000;

const ImageSkeleton = (props: PropsWithChildren<ImageSkeletonProps>) => {
  const { children, src, fallback, timeout = IMG_LOAD_TIMEOUT } = props;

  const [isLoading, _, finishLoading] = useBoolean(true);

  useEffect(() => {
    const image = new Image();

    image.onload = finishLoading;
    image.onerror = finishLoading;
    image.onloadstart = () => setTimeout(finishLoading, timeout);

    image.src = src;
  }, []);

  return isLoading ? fallback : children;
};

export default ImageSkeleton;
