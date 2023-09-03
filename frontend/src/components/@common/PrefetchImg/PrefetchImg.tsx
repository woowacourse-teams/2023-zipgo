import { useEffect } from 'react';

interface PrefetchImgProps {
  srcList: string[];
}

const PrefetchImg = (props: PrefetchImgProps) => {
  const { srcList } = props;

  useEffect(() => {
    srcList.forEach(src => {
      new Image().src = src;
    });
  }, []);

  return null;
};

export default PrefetchImg;
