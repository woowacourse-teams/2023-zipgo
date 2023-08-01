/* eslint-disable react/destructuring-assignment */
import { cloneElement, ComponentPropsWithoutRef, HTMLAttributes, PropsWithChildren } from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components';

import DialogProvider, { useDialogContext } from '@/context/Dialog/DialogContext';
import { getValidProps } from '@/utils/compound';
import { composeEventHandlers } from '@/utils/dom';

export interface DialogProps {
  open?: boolean;
  defaultOpen?: boolean;
  scroll?: boolean;
  onOpenChange?: (open: boolean) => void;
}

interface TriggerProps extends ComponentPropsWithoutRef<'button'> {
  asChild?: boolean;
}

interface PortalProps {
  container?: HTMLElement;
}

interface BackDropProps extends ComponentPropsWithoutRef<'div'> {
  asChild?: boolean;
}

interface ContentProps extends HTMLAttributes<HTMLDivElement> {
  asChild?: boolean;
}

interface CloseProps extends ComponentPropsWithoutRef<'button'> {
  asChild?: boolean;
}

const Dialog = (props: PropsWithChildren<DialogProps>) => {
  const { children, ...restProps } = props;

  return <DialogProvider value={restProps}>{children}</DialogProvider>;
};

const Trigger = (props: PropsWithChildren<TriggerProps>) => {
  const { asChild, child, onClick: onClickProps, ...restProps } = getValidProps(props);
  const { isOpened, openHandler } = useDialogContext();

  /** @todo aria-controls: dialog content id */
  const triggerA11y = {
    role: 'tablist',
    'aria-haspopup': 'dialog',
    'aria-expanded': isOpened,
  } as const;

  const trigger = asChild ? (
    cloneElement(child, {
      ...restProps,
      ...triggerA11y,
      onClick: composeEventHandlers(onClickProps, openHandler),
    })
  ) : (
    <button
      {...restProps}
      {...triggerA11y}
      type="button"
      onClick={composeEventHandlers(onClickProps, openHandler)}
    >
      {props.children || 'Trigger'}
    </button>
  );

  return trigger;
};

const Portal = ({ children, container = document.body }: PropsWithChildren<PortalProps>) => {
  const { isOpened } = useDialogContext();

  return isOpened ? createPortal(children, container) : null;
};

const BackDrop = (props: PropsWithChildren<BackDropProps>) => {
  const { asChild, child, onClick: onClickProps, ...restProps } = getValidProps(props);
  const { isOpened, openHandler } = useDialogContext();

  const backDropA11y = {
    'aria-hidden': true,
  } as const;

  const backDrop = asChild ? (
    cloneElement(child, {
      ...restProps,
      ...backDropA11y,
      onClick: composeEventHandlers(onClickProps, openHandler),
    })
  ) : (
    <DefaultBackDrop
      {...restProps}
      {...backDropA11y}
      onClick={composeEventHandlers(onClickProps, openHandler)}
      aria-hidden
    />
  );

  return isOpened ? backDrop : null;
};

const DefaultBackDrop = styled.div`
  ${({ theme }) => theme.componentStyle.backDrop}
`;

const Content = (props: PropsWithChildren<ContentProps>) => {
  const { asChild, child, ...restProps } = getValidProps(props);
  const { isOpened } = useDialogContext();

  const contentA11y = {
    role: 'dialog',
  } as const;

  const content = asChild ? (
    cloneElement(child, {
      ...restProps,
      ...contentA11y,
    })
  ) : (
    <div {...restProps} {...contentA11y}>
      {props.children}
    </div>
  );

  return isOpened ? content : null;
};

const Close = (props: PropsWithChildren<CloseProps>) => {
  const {
    asChild,
    child,
    onClick: onClickProps,
    'aria-label': ariaLabel,
    ...restProps
  } = getValidProps(props);
  const { isOpened, openHandler } = useDialogContext();

  const closeA11y = {
    'aria-label': ariaLabel || 'Close',
  } as const;

  const close = asChild ? (
    cloneElement(child, {
      ...restProps,
      ...closeA11y,
      onClick: composeEventHandlers(onClickProps, openHandler),
    })
  ) : (
    <button
      {...restProps}
      {...closeA11y}
      type="button"
      onClick={composeEventHandlers(onClickProps, openHandler)}
    >
      {props.children || 'X'}
    </button>
  );

  return isOpened ? close : null;
};

Dialog.Trigger = Trigger;
Dialog.Portal = Portal;
Dialog.BackDrop = BackDrop;
Dialog.Content = Content;
Dialog.Close = Close;

export { Dialog };
