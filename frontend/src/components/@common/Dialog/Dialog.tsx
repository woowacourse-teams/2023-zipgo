/* eslint-disable react/destructuring-assignment */
import { cloneElement, ComponentPropsWithoutRef, HTMLAttributes, PropsWithChildren } from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components';

import DialogProvider, { useDialogContext } from '@/context/Dialog/DialogContext';
import { getValidProps, PropsWithRenderProps } from '@/utils/compound';
import { composeFunctions } from '@/utils/dom';

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
  const { resolveChildren, onClick: onClickProps, ...restProps } = getValidProps(props);
  const { isOpened, openHandler } = useDialogContext();

  const resolved = resolveChildren({});

  /** @todo aria-controls: dialog content id */
  const triggerA11y = {
    role: 'button',
    'aria-haspopup': 'dialog',
    'aria-expanded': isOpened,
  } as const;

  const trigger = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...triggerA11y,
      onClick: composeFunctions(onClickProps, openHandler),
    })
  ) : (
    <button
      {...restProps}
      {...triggerA11y}
      type="button"
      onClick={composeFunctions(onClickProps, openHandler)}
    >
      {resolved.children || 'Trigger'}
    </button>
  );

  return trigger;
};

const Portal = ({ children, container = document.body }: PropsWithChildren<PortalProps>) => {
  const { isOpened } = useDialogContext();

  return isOpened ? createPortal(children, container) : null;
};

const BackDrop = (props: PropsWithChildren<BackDropProps>) => {
  const { resolveChildren, onClick: onClickProps, ...restProps } = getValidProps(props);
  const { isOpened, openHandler } = useDialogContext();

  const resolved = resolveChildren({});

  const backDropA11y = {
    'aria-hidden': true,
  } as const;

  const backDrop = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...backDropA11y,
      onClick: composeFunctions(onClickProps, openHandler),
    })
  ) : (
    <DefaultBackDrop
      {...restProps}
      {...backDropA11y}
      onClick={composeFunctions(onClickProps, openHandler)}
      aria-hidden
    />
  );

  return isOpened ? backDrop : null;
};

const DefaultBackDrop = styled.div`
  z-index: 1000;

  ${({ theme }) => theme.componentStyle.backDrop}
`;

const Content = (props: PropsWithRenderProps<ContentProps, { openHandler: VoidFunction }>) => {
  const { resolveChildren, ...restProps } = getValidProps(props);
  const { isOpened, openHandler } = useDialogContext();

  const resolved = resolveChildren({ openHandler });

  const contentA11y = {
    role: 'dialog',
  } as const;

  const content = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...contentA11y,
    })
  ) : (
    <div {...restProps} {...contentA11y}>
      {resolved.children}
    </div>
  );

  return isOpened ? content : null;
};

const Close = (props: PropsWithChildren<CloseProps>) => {
  const {
    resolveChildren,
    onClick: onClickProps,
    'aria-label': ariaLabel,
    ...restProps
  } = getValidProps(props);
  const { isOpened, openHandler } = useDialogContext();

  const resolved = resolveChildren({});

  const closeA11y = {
    'aria-label': ariaLabel || 'Close',
  } as const;

  const close = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...closeA11y,
      onClick: composeFunctions(onClickProps, openHandler),
    })
  ) : (
    <button
      {...restProps}
      {...closeA11y}
      type="button"
      onClick={composeFunctions(onClickProps, openHandler)}
    >
      {resolved.children || 'X'}
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
