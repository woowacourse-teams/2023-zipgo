import { useQuery } from '@tanstack/react-query';
import { ComponentPropsWithoutRef } from 'react';

interface SuspendedImgProps extends ComponentPropsWithoutRef<'img'> {
  src: string;
  staleTime?: number;
  cacheTime?: number;
}

const SuspendedImg = (props: SuspendedImgProps) => {
  const { src, cacheTime, staleTime, ...restProps } = props;

  const img = new Image();

  useQuery({
    queryKey: [src],
    queryFn: () =>
      new Promise(resolve => {
        img.onload = resolve;

        img.src = src;
      }),
    ...(staleTime == null ? {} : { staleTime }),
    ...(cacheTime == null ? {} : { cacheTime }),
  });

  // eslint-disable-next-line jsx-a11y/alt-text
  return <img src={src} {...restProps} />;
};

export default SuspendedImg;
