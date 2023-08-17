import { cloneElement, ComponentPropsWithoutRef, useId } from 'react';

import TabsProvider, { useTabsContext } from '@/context/Tabs/TabsContext';
import type { AsChild, PropsWithAsChild, PropsWithRenderProps } from '@/utils/compound';
import { getValidProps } from '@/utils/compound';
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
  const { resolveChildren, ...restProps } = getValidProps(props);

  const resolved = resolveChildren({});

  const listA11y = {
    role: 'tablist',
    'aria-orientation': 'horizontal',
  } as const;

  const list = resolved.asChild ? (
    cloneElement(resolved.child, {
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

const Trigger = (props: PropsWithRenderProps<TriggerProps & AsChild, { selected: boolean }>) => {
  const {
    resolveChildren,
    value,
    id: idProps,
    onClick: onClickProps,
    ...restProps
  } = getValidProps(props);

  const uuid = useId();

  const { selectedValue, changeTab } = useTabsContext();

  const onClick = composeEventHandlers(onClickProps, () => changeTab(value));

  const selected = value === selectedValue;

  const id = idProps ?? `trigger-${value}-${uuid}`;

  const resolved = resolveChildren({ selected });

  /** @todo aria-controls: tab content id */
  const triggerA11y = {
    role: 'tab',
    'aria-selected': selected,
  } as const;

  const trigger = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...triggerA11y,
      onClick,
      id,
    })
  ) : (
    <button {...restProps} {...triggerA11y} id={id} type="button" onClick={onClick}>
      {resolved.children || 'Tab'}
    </button>
  );

  return trigger;
};

interface ContentProps extends ComponentPropsWithoutRef<'section'> {
  value: string;
}

const Content = (props: PropsWithRenderProps<ContentProps>) => {
  const { resolveChildren, value, id: idProps, ...restProps } = getValidProps(props);

  const uuid = useId();

  const { selectedValue } = useTabsContext();

  const id = idProps ?? `content-${value}-${uuid}`;

  const resolved = resolveChildren({});

  /** @todo aria-labelledby: tab trigger id */
  const contentA11y = {
    role: 'tabpanel',
    'aria-orientation': 'horizontal',
  } as const;

  const content = resolved.asChild ? (
    cloneElement(resolved.child, {
      ...restProps,
      ...contentA11y,
      hidden: selectedValue !== value,
      id,
    })
  ) : (
    <section {...restProps} {...contentA11y} id={id} hidden={selectedValue !== value}>
      {resolved.children}
    </section>
  );

  return content;
};

Tabs.List = List;
Tabs.Trigger = Trigger;
Tabs.Content = Content;

export default Tabs;
