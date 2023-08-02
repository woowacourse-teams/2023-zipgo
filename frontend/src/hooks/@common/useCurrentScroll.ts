import { useEffect, useState } from 'react';

const useCurrentScroll = (): number => {
  const [scrollPosition, setScrollPosition] = useState<number>(0);

  const onScroll = () => {
    setScrollPosition(window.scrollY);
  };

  useEffect(() => {
    window.addEventListener('scroll', onScroll);
    return () => {
      window.removeEventListener('scroll', onScroll);
    };
  }, []);

  return scrollPosition;
};

export default useCurrentScroll;
