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

import ToastWarningIcon from '@/assets/svg/toast_warning_icon.svg';
import PrefetchImg from '@/components/@common/PrefetchImg/PrefetchImg';
import Toast from '@/components/@common/Toast/Toast';
import { Type } from '@/constants/toast';
import { Toast as ToastInterface, ToastContent, ToastId, ToastOption } from '@/types/toast/client';

type CreateToastFunction = (content: ToastContent, option?: ToastOption) => ToastInterface;

interface CreateToastByType {
  success: CreateToastFunction;
  info: CreateToastFunction;
  warning: CreateToastFunction;
}

interface ToastContext {
  toast: CreateToastByType;
  currentToast: ToastInterface[];
}

const createInitialToastByType =
  (type: Type): CreateToastFunction =>
  (content, option) => ({
    content: '',
    option: {
      toastId: -1,
      type,
    },
  });

const initialToast: CreateToastByType = {
  success: createInitialToastByType(Type.SUCCESS),
  info: createInitialToastByType(Type.INFO),
  warning: createInitialToastByType(Type.WARNING),
};

const ToastContext = createContext<ToastContext>({
  toast: initialToast,
  currentToast: [],
});

ToastContext.displayName = 'Toast';

export const useToast = () => useContext(ToastContext);

const Portal = ({ children }: PropsWithChildren) => createPortal(children, document.body);

const ToastProvider = (props: PropsWithChildren) => {
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
    currentToast.current = currentToast.current.filter(toast => toast.option.toastId !== toastId);

    setToastStates(currentToast.current);
    timers.current.delete(toastId);
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

  const createToastByType = useCallback(
    (type: Type) => (content: ToastContent, option?: ToastOption) =>
      dispatchToast(content, mergeOptions(type, option)),
    [dispatchToast, mergeOptions],
  );

  const toast: CreateToastByType = useMemo(
    () => ({
      success: createToastByType(Type.SUCCESS),
      info: createToastByType(Type.INFO),
      warning: createToastByType(Type.WARNING),
    }),
    [createToastByType],
  );

  const memoizedValue = useMemo(
    () => ({
      toast,
      currentToast: toastStates,
    }),
    [toast, toastStates],
  );

  return (
    <ToastContext.Provider value={memoizedValue}>
      <PrefetchImg srcList={[ToastWarningIcon]} />
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
