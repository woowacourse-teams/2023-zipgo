import { useEffect, useRef, useState } from 'react';

import { Type } from '@/constants/toast';
import { Toast, ToastContent, ToastId, ToastOption } from '@/types/toast/client';

const useToast = () => {
  const [toastStates, setToastStates] = useState<Toast[]>([]);
  const currentToast = useRef<Toast[]>([]);

  const toastId = useRef<ToastId>(0);

  const timers = useRef<Map<ToastId, NodeJS.Timeout>>(new Map<ToastId, NodeJS.Timeout>());

  const deleteToastId = useRef<ToastId>(0);

  const appendToast = (toast: Toast) => {
    currentToast.current = [...currentToast.current, toast];

    toastId.current += 1;

    const timer = setTimeout(() => {
      deleteToast(toast.option.toastId);
    }, 4500);

    timers.current.set(toast.option.toastId, timer);

    setToastStates(currentToast.current);
  };

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

  const getToastId = (options?: ToastOption): ToastId =>
    options?.toastId ? options.toastId : generateToastId();

  const mergeOptions = (type: Type, options?: ToastOption): ToastOption => ({
    type: (options && options.type) || type,
    toastId: getToastId(options),
  });

  const createToastByType = (type: Type) => (content: ToastContent, option?: ToastOption) =>
    dispatchToast(content, mergeOptions(type, option));

  const dispatchToast = (content: ToastContent, option: ToastOption): Toast => {
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
  };

  const toast = (content: ToastContent, option?: ToastOption) =>
    dispatchToast(content, mergeOptions(Type.INFO, option));

  toast.success = createToastByType(Type.SUCCESS);
  toast.info = createToastByType(Type.INFO);
  toast.warning = createToastByType(Type.WARNING);

  return { toast, currentToast: toastStates };
};

export default useToast;
