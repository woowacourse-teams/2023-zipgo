import { RefObject, useEffect, useState } from 'react';

const useCurrentScroll = ({
  ref,
  element,
}: {
  ref?: RefObject<HTMLDivElement>;
  element?: HTMLElement;
}): number => {
  const [scrollPosition, setScrollPosition] = useState<number>(0);

  useEffect(() => {
    const onScroll = () => {
      if (ref?.current) {
        const absoluteTop = ref.current.scrollTop;
        setScrollPosition(absoluteTop);
      }
      if (element) {
        const absoluteTop = element.scrollTop;
        setScrollPosition(absoluteTop);
      }
    };

    if (ref) {
      ref.current?.addEventListener('scroll', onScroll);
    }
    if (element) {
      element.addEventListener('scroll', onScroll);
    }
  }, [ref, element]);

  return scrollPosition;
};

export default useCurrentScroll;
