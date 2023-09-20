import {
  createContext,
  PropsWithChildren,
  useCallback,
  useContext,
  useMemo,
  useRef,
  useState,
} from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components';

import Toast from '@/components/@common/Toast/Toast';
import { Type } from '@/constants/toast';
import { Toast as ToastInterface, ToastContent, ToastId, ToastOption } from '@/types/toast/client';

interface ToastContext {
  toast: {
    (content: ToastContent, option?: ToastOption): ToastInterface;
    success: (content: ToastContent, option?: ToastOption) => ToastInterface;
    info: (content: ToastContent, option?: ToastOption) => ToastInterface;
    warning: (content: ToastContent, option?: ToastOption) => ToastInterface;
  };
  currentToast: ToastInterface[];
}

const tempToast = (content: ToastContent, option?: ToastOption): ToastInterface => ({
  content: '',
  option: {
    toastId: -1,
    type: Type.INFO,
  },
});

tempToast.success = (content: ToastContent, option?: ToastOption): ToastInterface => ({
  content: '',
  option: {
    toastId: -1,
    type: Type.SUCCESS,
  },
});

tempToast.info = (content: ToastContent, option?: ToastOption): ToastInterface => ({
  content: '',
  option: {
    toastId: -1,
    type: Type.INFO,
  },
});

tempToast.warning = (content: ToastContent, option?: ToastOption): ToastInterface => ({
  content: '',
  option: {
    toastId: -1,
    type: Type.WARNING,
  },
});

const ToastContext = createContext<ToastContext>({
  toast: tempToast,
  currentToast: [],
});

ToastContext.displayName = 'Toast';

export const useToast = () => useContext(ToastContext);

interface ToastProviderProps {}

const Portal = ({ children }: PropsWithChildren) => createPortal(children, document.body);

const ToastProvider = (props: PropsWithChildren<ToastProviderProps>) => {
  const { children } = props;

  const [toastStates, setToastStates] = useState<ToastInterface[]>([]);
  const currentToast = useRef<ToastInterface[]>([]);

  const toastId = useRef<ToastId>(0);

  const timers = useRef<Map<ToastId, NodeJS.Timeout>>(new Map<ToastId, NodeJS.Timeout>());

  const deleteToastId = useRef<ToastId>(0);

  const appendToast = useCallback((toast: ToastInterface) => {
    currentToast.current = [...currentToast.current, toast];

    toastId.current += 1;

    const timer = setTimeout(() => {
      deleteToast(toast.option.toastId);
    }, 4500);

    timers.current.set(toast.option.toastId, timer);

    setToastStates(currentToast.current);
  }, []);

  const deleteToast = (toastId: ToastId) => {
    const targetToastIndex = currentToast.current.findIndex(
      toast => toast.option.toastId === toastId,
    );
    if (targetToastIndex !== -1) {
      const copyArr = currentToast.current.slice();
      copyArr.splice(targetToastIndex, 1);

      currentToast.current = copyArr;

      setToastStates(currentToast.current);

      timers.current.delete(toastId);
    }
  };

  const generateToastId = (): ToastId => toastId.current;

  const getToastId = useCallback(
    (options?: ToastOption): ToastId => (options?.toastId ? options.toastId : generateToastId()),
    [],
  );

  const mergeOptions = useCallback(
    (type: Type, options?: ToastOption): ToastOption => ({
      type: (options && options.type) || type,
      toastId: getToastId(options),
    }),
    [getToastId],
  );

  const createToastByType = (type: Type) => (content: ToastContent, option?: ToastOption) =>
    dispatchToast(content, mergeOptions(type, option));

  const dispatchToast = useCallback(
    (content: ToastContent, option: ToastOption): ToastInterface => {
      appendToast({ content, option });

      if (currentToast.current.length >= 5) {
        currentToast.current[deleteToastId.current].deleted = true;
        deleteToastId.current += 1;
        setToastStates(currentToast.current);

        clearTimeout(Array.from(timers.current.values())[0]);

        setTimeout(() => {
          deleteToast(currentToast.current[0].option.toastId);
          deleteToastId.current -= 1;
        }, 500);
      }

      return { content, option };
    },
    [appendToast],
  );

  const toast = (content: ToastContent, option?: ToastOption) =>
    dispatchToast(content, mergeOptions(Type.INFO, option));

  toast.success = createToastByType(Type.SUCCESS);
  toast.info = createToastByType(Type.INFO);
  toast.warning = createToastByType(Type.WARNING);

  const memoizedValue = useMemo(
    () => ({
      toast,
      currentToast: toastStates,
    }),
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [toastStates],
  );

  return (
    <ToastContext.Provider value={memoizedValue}>
      {children}
      <Portal>
        <ToastContainerWrapper>
          {toastStates.map(toast => (
            <Toast
              key={toast.option.toastId}
              type={toast.option.type}
              content={toast.content}
              deleted={toast.deleted}
            />
          ))}
        </ToastContainerWrapper>
      </Portal>
    </ToastContext.Provider>
  );
};

export default ToastProvider;

const ToastContainerWrapper = styled.div`
  position: fixed;
  bottom: 3.2rem;
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;

  transition: all 0.5s ease;
`;
