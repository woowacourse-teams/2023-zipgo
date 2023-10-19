import { useEffect, useState } from 'react';

const useDesktopView = () => {
  const [clientWidth, setClientWidth] = useState<number>(document.body.clientWidth);

  const resizeListener = () => {
    const width = document.body.clientWidth;
    setClientWidth(width);
  };

  useEffect(() => {
    window.addEventListener('resize', resizeListener);

    return () => window.removeEventListener('resize', resizeListener);
  });

  return { clientWidth };
};

export default useDesktopView;
