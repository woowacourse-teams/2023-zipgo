import { useMutation, UseMutationOptions } from '@tanstack/react-query';

import { useToast } from '@/context/Toast/ToastContext';
import { ZipgoError } from '@/utils/errors';

/** @description useMutation의 오버로딩 타입 중 options만 사용하는 형태로 사용한다 */
const useMutationWithToast = <
  TData = unknown,
  TError = unknown,
  TVariables = void,
  TContext = unknown,
>(
  options: UseMutationOptions<TData, TError, TVariables, TContext>,
) => {
  const { toast } = useToast();

  return useMutation({
    ...options,
    onError(...args) {
      const [rejectedValue] = args;

      const error = ZipgoError.convertToError(rejectedValue);

      options.onError?.(...args);

      toast.warning(error.message);
    },
  });
};

export default useMutationWithToast;
