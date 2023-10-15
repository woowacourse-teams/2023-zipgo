import useBoolean from '../@common/useBoolean';

let isOpenGlobally = true;

export const useFoodSelectionGuideBanner = () => {
  const [isOpen, , close] = useBoolean(isOpenGlobally);

  const closeBanner = () => {
    close();
    isOpenGlobally = false;
  };

  return { isOpen, closeBanner };
};
