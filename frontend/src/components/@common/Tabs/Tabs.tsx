import { cloneElement, ComponentPropsWithoutRef, useId } from 'react';

import TabsProvider, { useTabsContext } from '@/context/Tabs/TabsContext';
import { getValidProps, PropsWithAsChild } from '@/utils/compound';
import { composeEventHandlers } from '@/utils/dom';

export interface TabProps {
  defaultValue: string;
  onValueChange?: (value: string) => void;
}

const Tabs = (props: PropsWithAsChild<TabProps>) => {
  const { children, ...restProps } = props;

  return <TabsProvider value={restProps}>{children}</TabsProvider>;
};

interface ListProps extends ComponentPropsWithoutRef<'div'> {}

const List = (props: PropsWithAsChild<ListProps>) => {
  const { asChild, child, ...restProps } = getValidProps(props);

  const listA11y = {
    role: 'tablist',
    'aria-orientation': 'horizontal',
  } as const;

  const list = asChild ? (
    cloneElement(child, {
      ...restProps,
      ...listA11y,
    })
  ) : (
    <div {...restProps} {...listA11y}>
      {props.children}
    </div>
  );

  return list;
};

interface TriggerProps extends ComponentPropsWithoutRef<'button'> {
  value: string;
}

const Trigger = (props: PropsWithAsChild<TriggerProps>) => {
  const {
    asChild,
    child,
    value,
    id: idProps,
    onClick: onClickProps,
    ...restProps
  } = getValidProps(props);

  const uuid = useId();

  const { selectedValue, changeTab } = useTabsContext();

  const onClick = composeEventHandlers(onClickProps, () => changeTab(value));

  const id = idProps ?? `trigger-${value}-${uuid}`;

  /** @todo aria-controls: tab content id */
  const triggerA11y = {
    role: 'tab',
    'aria-selected': value === selectedValue,
  } as const;

  const trigger = asChild ? (
    cloneElement(child, {
      ...restProps,
      ...triggerA11y,
      onClick,
      id,
    })
  ) : (
    <button {...restProps} {...triggerA11y} id={id} type="button" onClick={onClick}>
      {props.children || 'Tab'}
    </button>
  );

  return trigger;
};

interface ContentProps extends ComponentPropsWithoutRef<'section'> {
  value: string;
}

const Content = (props: PropsWithAsChild<ContentProps>) => {
  const { asChild, child, value, id: idProps, ...restProps } = getValidProps(props);

  const uuid = useId();

  const { selectedValue } = useTabsContext();

  const id = idProps ?? `content-${value}-${uuid}`;

  /** @todo aria-labelledby: tab trigger id */
  const contentA11y = {
    role: 'tabpanel',
    'aria-orientation': 'horizontal',
  } as const;

  const trigger = asChild ? (
    cloneElement(child, {
      ...restProps,
      ...contentA11y,
      hidden: selectedValue !== value,
      id,
    })
  ) : (
    <section {...restProps} {...contentA11y} id={id} hidden={selectedValue !== value}>
      {props.children}
    </section>
  );

  return trigger;
};

Tabs.List = List;
Tabs.Trigger = Trigger;
Tabs.Content = Content;

export default Tabs;
