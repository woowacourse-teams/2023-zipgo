import { ReactElement } from 'react';

import { Type } from '@/constants/toast';

export type ToastContent = string | ReactElement;

export type ToastType = Type;

export interface ToastProps {
  content: ToastContent;
  type: ToastType;
  deleted?: boolean;
}

export interface ToastIconProps extends Pick<ToastProps, 'type'> {}

export type ToastId = number;

export interface ToastOption extends Pick<ToastProps, 'type'> {
  toastId: ToastId;
}

export interface Toast {
  content: ToastContent;
  option: ToastOption;
  deleted?: boolean;
}
