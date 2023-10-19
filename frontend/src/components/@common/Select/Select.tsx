import { cloneElement, ComponentPropsWithoutRef, PropsWithChildren, useEffect, useId } from 'react';
import { createPortal } from 'react-dom';
import styled from 'styled-components';

import SelectProvider, { useSelectContext } from '@/context/Select/SelectContext';
import { AsChild, getValidProps, PropsWithAsChild, PropsWithRenderProps } from '@/utils/compound';
import { composeFunctions } from '@/utils/dom';

export interface SelectProps {
  defaultValue?: string;
  onOpenChange?: (open: boolean) => void;
}

export interface SelectPropsWithValue extends SelectProps {
  value: string;
  onValueChange: (value: string) => void;
}

const Select = (props: PropsWithAsChild<SelectProps | SelectPropsWithValue>) => {
  const { children, ...restProps } = props;

  return <SelectProvider value={restProps}>{children}</SelectProvider>;
};

interface TriggerProps extends ComponentPropsWithoutRef<'button'> {}

const Trigger = (
  props: PropsWithRenderProps<TriggerProps & AsChild, { selectedValue: string }>,
) => {
  const {
    resolveChildren,
    id: idProps,
    onClick: onClickProps,
    ...restProps
  } = getValidProps(props);

  const { selectedValue, isOpened, openHandler } = useSelectContext();

  const uuid = useId();

  const id = idProps ?? `trigger-${selectedValue}-${uuid}`;

  const resolved = resolveChildren({ selectedValue });

  /** @todo aria-controls: tab content id */
  const triggerA11y = {
    role: 'combobox',
    'aria-expanded': isOpened,
  } as const;

  const trigger = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...triggerA11y,
      id,
      onClick: composeFunctions(onClickProps, openHandler),
    })
  ) : (
    <button
      {...restProps}
      {...triggerA11y}
      id={id}
      type="button"
      onClick={composeFunctions(onClickProps, openHandler)}
    >
      {resolved.children || 'Tab'}
    </button>
  );

  return trigger;
};

interface PortalProps {
  container?: HTMLElement;
}

const Portal = ({ children, container = document.body }: PropsWithChildren<PortalProps>) => {
  const { isOpened } = useSelectContext();

  return isOpened ? createPortal(children, container) : null;
};

interface ContentProps extends ComponentPropsWithoutRef<'div'> {
  onClose?: VoidFunction;
}

const Content = (props: PropsWithAsChild<ContentProps>) => {
  const { resolveChildren, onClose, id: idProps, ...restProps } = getValidProps(props);

  const { isOpened, openHandler } = useSelectContext();

  const uuid = useId();

  const id = idProps ?? `content-${uuid}`;

  const resolved = resolveChildren({});

  /** @todo aria-labelledby: tab trigger id */
  const contentA11y = {
    role: 'listbox',
  } as const;

  const content = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...contentA11y,
      id,
    })
  ) : (
    <div {...restProps} {...contentA11y} id={id}>
      {resolved.children}
    </div>
  );

  useEffect(() => () => {
    onClose?.();
  });

  return isOpened ? (
    <>
      {createPortal(<BackDrop onClick={openHandler} />, document.body)}
      {content}
    </>
  ) : null;
};

const BackDrop = styled.div`
  position: fixed;
  z-index: 9999;
  top: 0;

  width: 100%;
  height: calc((var(--vh, 1vh) * 100));
`;

interface ItemProps extends ComponentPropsWithoutRef<'div'> {
  value: string;
}

const Item = (props: PropsWithRenderProps<ItemProps, { selected: boolean }>) => {
  const {
    resolveChildren,
    value,
    id: idProps,
    onClick: onClickProps,
    ...restProps
  } = getValidProps(props);

  const uuid = useId();

  const { selectedValue, changeValue, openHandler } = useSelectContext();

  const onClick = composeFunctions(onClickProps, () => {
    changeValue(value);
    openHandler();
  });

  const selected = value === selectedValue;

  const id = idProps ?? `item-${value}-${uuid}`;

  const resolved = resolveChildren({ selected });

  /** @todo aria-labelledby: tab trigger id */
  const itemA11y = {
    role: 'option',
    'aria-selected': selected,
  } as const;

  const content = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...itemA11y,
      onClick,
      id,
    })
  ) : (
    <div {...restProps} {...itemA11y} id={id} onClick={onClick}>
      {resolved.children}
    </div>
  );

  return content;
};

Select.Trigger = Trigger;
Select.Portal = Portal;
Select.Content = Content;
Select.Item = Item;

export default Select;
